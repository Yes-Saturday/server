package com.saturday.web.controller;

import com.saturday.common.annotation.Verify;
import com.saturday.common.constant.ErrorCodeConstant;
import com.saturday.common.domain.FrontEndResponse;
import com.saturday.common.exception.BusinessException;
import com.saturday.common.utils.BaseRSA;
import com.saturday.common.utils.CacheUtil;
import com.saturday.common.utils.PasswordHandler;
import com.saturday.system.domain.info.PublisherInfo;
import com.saturday.system.service.PublisherService;
import com.saturday.web.constant.SystemConstant;
import com.saturday.web.domain.request.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@ResponseBody
public class LoginController {

    @Autowired
    private PublisherService publisherService;

    @PostMapping("/doLogin")
    public FrontEndResponse login(@Verify LoginRequest loginRequest, HttpServletRequest request) {
        byte[] privateKey = CacheUtil.get(SystemConstant.PRIVATE_KEY_ALIAS);
        if (privateKey == null) {
            throw new BusinessException(ErrorCodeConstant.PUBLIC_KEY_INVALID, "公钥过期");
        }

        String pwd = loginRequest.getPassword();
        try {
            pwd = PasswordHandler.getPwd(BaseRSA.decryptByPrivateKey(Base64Utils.decodeFromString(pwd), privateKey));
        } catch (Exception e) {
            throw new BusinessException(ErrorCodeConstant.RSA_ERROR, "解密失败");
        }

        PublisherInfo publisherInfo = publisherService.query4Login(loginRequest.getLoginName(), pwd);

        if (publisherInfo == null)
            throw new BusinessException(ErrorCodeConstant.PASSWORD_WRONG, "密码错误");

        request.getSession().setAttribute("publisher", publisherInfo);


        return FrontEndResponse.success(publisherInfo);
    }

    @PostMapping("/doLogout")
    public FrontEndResponse logout(HttpServletRequest request) {
        var session = request.getSession(false);
        if (session != null)
            session.setAttribute("publisher", null);
        return FrontEndResponse.success();
    }
}