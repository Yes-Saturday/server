package com.ggboy.web.controller;

import com.ggboy.common.annotation.Verify;
import com.ggboy.common.constant.ErrorCodeConstant;
import com.ggboy.common.domain.FrontEndResponse;
import com.ggboy.common.exception.BusinessException;
import com.ggboy.common.exception.CommonUtilException;
import com.ggboy.common.utils.BaseRSA;
import com.ggboy.common.utils.CacheUtil;
import com.ggboy.common.utils.PasswordHandler;
import com.ggboy.common.utils.StringUtil;
import com.ggboy.system.domain.info.PublisherInfo;
import com.ggboy.system.service.PublisherService;
import com.ggboy.web.constant.SystemConstant;
import com.ggboy.web.domain.request.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@ResponseBody
public class LoginController {

    @Autowired
    private PublisherService publisherService;

    @PostMapping("/login")
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

    @PostMapping("/logout")
    public FrontEndResponse logout(HttpServletRequest request) {
        var session = request.getSession(false);
        if (session != null)
            session.setAttribute("publisher", null);
        return FrontEndResponse.success();
    }
}