package com.ggboy.common.utils;

import com.ggboy.common.exception.CommonUtilException;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class BaseRSA {
    // 签名算法
    public static final String SIGNATURE_ALGORITHM = "SHA256withRSA";
    // 加密算法RSA
    public static final String KEY_ALGORITHM = "RSA";
    // RSA最大加密明文大小
    private static final int MAX_ENCRYPT_BLOCK = 117;
    // RSA最大解密密文大小
    private static final int MAX_DECRYPT_BLOCK = 128;
    // 获取公钥的key
    private static final String PUBLIC_KEY = "public_key";
    // 获取私钥的key
    private static final String PRIVATE_KEY = "private_key";

    public static byte[] sign(byte[] data, byte[] privateKey) throws CommonUtilException {
        try {
            return getSignature(data, privateKey, PRIVATE_KEY).sign();
        } catch (Exception e) {
            throw new CommonUtilException(e);
        }
    }

    public static boolean verify(byte[] data, byte[] sign, byte[] publicKey) throws CommonUtilException {
        try {
            return getSignature(data, publicKey, PUBLIC_KEY).verify(sign);
        } catch (Exception e) {
            throw new CommonUtilException(e);
        }
    }

    private static Signature getSignature(byte[] data, byte[] key, String wKey) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException {
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        switch (wKey) {
            case PUBLIC_KEY:
                signature.initVerify(keyFactory.generatePublic(new X509EncodedKeySpec(key)));
                break;
            case PRIVATE_KEY:
                signature.initSign(keyFactory.generatePrivate(new PKCS8EncodedKeySpec(key)));
                break;
        }
        signature.update(data);
        return signature;
    }

    public static byte[] decryptByPublicKey(byte[] data, byte[] key) throws CommonUtilException {
        return rsaAlgorithm(data, key, PUBLIC_KEY, Cipher.DECRYPT_MODE, MAX_DECRYPT_BLOCK);
    }

    public static byte[] decryptByPrivateKey(byte[] data, byte[] key) throws CommonUtilException {
        return rsaAlgorithm(data, key, PRIVATE_KEY, Cipher.DECRYPT_MODE, MAX_DECRYPT_BLOCK);
    }

    public static byte[] encryptByPublicKey(byte[] data, byte[] key) throws CommonUtilException {
        return rsaAlgorithm(data, key, PUBLIC_KEY, Cipher.ENCRYPT_MODE, MAX_ENCRYPT_BLOCK);
    }

    public static byte[] encryptByPrivateKey(byte[] data, byte[] key) throws CommonUtilException {
        return rsaAlgorithm(data, key, PRIVATE_KEY, Cipher.ENCRYPT_MODE, MAX_ENCRYPT_BLOCK);
    }

    /**
     * 密文算法
     *
     * @param data
     * @param key
     * @param wKey
     * @param mode
     * @return byte[]
     * @throws CommonUtilException
     */
    private static byte[] rsaAlgorithm(byte[] data, byte[] key, String wKey, int mode, int block) throws CommonUtilException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            Key k = null;
            switch (wKey) {
                case PUBLIC_KEY:
                    k = keyFactory.generatePublic(new X509EncodedKeySpec(key));
                    break;
                case PRIVATE_KEY:
                    k = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(key));
                    break;
            }
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(mode, k);
            int inputLen = data.length;
            int offSet = 0;
            byte[] cache = null;
            int i = 0;
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > block) {
                    cache = cipher.doFinal(data, offSet, block);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * block;
            }
            return out.toByteArray();
        } catch (Exception e) {
            throw new CommonUtilException(e);
        } finally {
            if (out != null)
                try {
                    out.close();
                } catch (IOException e) {
                }
        }
    }

    public static byte[] getPrivateKey(Map<String, Object> keyMap) {
        return ((Key) keyMap.get(PRIVATE_KEY)).getEncoded();
    }

    public static byte[] getPublicKey(Map<String, Object> keyMap) {
        return ((Key) keyMap.get(PUBLIC_KEY)).getEncoded();
    }

    public static Map<String, Object> genKeyPair() throws CommonUtilException {
        try {
            KeyPairGenerator keyPairGen;
            keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
            keyPairGen.initialize(1024);
            KeyPair keyPair = keyPairGen.generateKeyPair();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            Map<String, Object> keyMap = new HashMap<String, Object>(2);
            keyMap.put(PUBLIC_KEY, publicKey);
            keyMap.put(PRIVATE_KEY, privateKey);
            return keyMap;
        } catch (Exception e) {
            throw new CommonUtilException(e);
        }
    }
}