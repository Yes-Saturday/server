package com.saturday.system.service;

import com.saturday.common.constant.ErrorCodeConstant;
import com.saturday.common.exception.CommonUtilException;
import com.saturday.common.exception.InternalException;
import com.saturday.common.utils.BaseRSA;
import com.saturday.common.utils.CacheUtil;
import com.saturday.common.utils.StringUtil;
import com.saturday.system.domain.info.RsaKeyInfo;
import org.springframework.stereotype.Service;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service
public class RsaService {

    private final static ReadWriteLock lock = new ReentrantReadWriteLock();
    public final static String RSA_KEY_ALIAS = "RsaKey#>";
    public final static int RSA_EXP_TIME = 5 * 60;
    public final static int PRIVATE_RSA_EXP_TIME = 60;


    public String getPublicKey() {
        return getPublicKey(null);
    }

    public String getPublicKey(String ext) {
        RsaKeyInfo rsaKeyInfo;

        try {
            lock.readLock().lock();
            rsaKeyInfo = CacheUtil.get(RSA_KEY_ALIAS);
        } finally {
            lock.readLock().unlock();
        }

        if (rsaKeyInfo == null)
            try {
                lock.writeLock().lock();
                rsaKeyInfo = CacheUtil.get(RSA_KEY_ALIAS);
                if (rsaKeyInfo == null)
                    try {
                        var keyMap = BaseRSA.genKeyPair();
                        rsaKeyInfo = new RsaKeyInfo(BaseRSA.getPublicKey(keyMap), BaseRSA.getPrivateKey(keyMap));
                        CacheUtil.put(RSA_KEY_ALIAS, rsaKeyInfo, RSA_EXP_TIME);
                    } catch (CommonUtilException e) {
                        throw new InternalException(ErrorCodeConstant.RSA_ERROR, "生成密钥对失败");
                    }
            } finally {
                lock.writeLock().unlock();
            }

        setPrivateKey(ext, PRIVATE_RSA_EXP_TIME);

        return rsaKeyInfo.getPublicKey();
    }

    public byte[] getPrivateKey() {
        return getPrivateKey(null);
    }

    public byte[] getPrivateKey(String ext) {
        var rsaKeyInfo = CacheUtil.get(RSA_KEY_ALIAS + (ext == null ? "" : ext));
        return rsaKeyInfo != null ? ((RsaKeyInfo) rsaKeyInfo).getPrivateKey() : null;
    }

    private void setPrivateKey(String ext, int expTime) {
        if (!StringUtil.isEmpty(ext))
            CacheUtil.copyToNewKey(RSA_KEY_ALIAS, RSA_KEY_ALIAS + ext, expTime);
    }
}
