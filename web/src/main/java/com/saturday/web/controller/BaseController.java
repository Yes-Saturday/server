package com.saturday.web.controller;

import com.saturday.common.constant.ErrorCodeConstant;
import com.saturday.common.domain.FrontEndResponse;
import com.saturday.common.exception.CommonUtilException;
import com.saturday.common.exception.InternalException;
import com.saturday.common.utils.BaseRSA;
import com.saturday.common.utils.CacheUtil;
import com.saturday.common.utils.StringUtil;
import com.saturday.web.constant.SystemConstant;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class BaseController {

    @GetMapping("/publicKey")
    public FrontEndResponse publicKey() {
        String publicKey = CacheUtil.get(SystemConstant.PUBLIC_KEY_ALIAS);
        if (StringUtil.isEmpty(publicKey)) {
            synchronized (SystemConstant.PUBLIC_KEY_ALIAS) {
                publicKey = CacheUtil.get(SystemConstant.PUBLIC_KEY_ALIAS);
                if (StringUtil.isEmpty(publicKey)) {
                    try {
                        var keyMap = BaseRSA.genKeyPair();
                        publicKey = Base64Utils.encodeToString(BaseRSA.getPublicKey(keyMap));
                        CacheUtil.put(SystemConstant.PUBLIC_KEY_ALIAS, publicKey, 5 * 60);
                        CacheUtil.put(SystemConstant.PRIVATE_KEY_ALIAS, BaseRSA.getPrivateKey(keyMap), 5 * 60);
                    } catch (CommonUtilException e) {
                        throw new InternalException(ErrorCodeConstant.RSA_ERROR, "生成密钥对失败");
                    }
                }
            }
        }
        return FrontEndResponse.success(publicKey);
    }

    @GetMapping("/cache-clear")
    public FrontEndResponse cacheClear() {
        CacheUtil.clear();
        return FrontEndResponse.success();
    }

    @GetMapping("/cache-list")
    public FrontEndResponse cacheList() {
        return FrontEndResponse.success(CacheUtil.list());
    }
}