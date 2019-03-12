package com.saturday.web.controller;

import com.saturday.common.annotation.Verify;
import com.saturday.common.domain.FrontEndResponse;
import com.saturday.common.utils.BaseRSA;
import com.saturday.common.utils.CacheUtil;
import com.saturday.common.utils.PasswordHandler;
import com.saturday.system.service.RsaService;
import com.saturday.user.service.UserService;
import com.saturday.web.domain.request.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController extends BaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private RsaService rsaService;

    @PostMapping("/doLogin")
    public FrontEndResponse login(@Verify LoginRequest loginRequest) {
        // TODO
        Object loginError = CacheUtil.get("loginError");
        if (loginError != null)
            return FrontEndResponse.fail("000", "登陆频繁");

        byte[] privateKey = rsaService.getPrivateKey(getSessionId());
        if (privateKey == null)
            return FrontEndResponse.fail("400", "公钥过期");

        String pwd = loginRequest.getPassword();
        try {
            pwd = PasswordHandler.getPwd(BaseRSA.decryptByPrivateKey(Base64Utils.decodeFromString(pwd), privateKey));
        } catch (Exception e) {
            return FrontEndResponse.fail("400", "RSA解密失败");
        }

        var userBasics = userService.queryUser(loginRequest.getLoginNumber(), pwd);

        if (userBasics == null) {
            // TODO
            CacheUtil.put("loginError", new Object(), 5 * 60);
            return FrontEndResponse.fail("000", "用户名或密码错误");
        }

        setSessionAttribute("user", userBasics);
        getSession().setMaxInactiveInterval(3600);
        return FrontEndResponse.success();
    }

    @GetMapping("/doLogout")
    public FrontEndResponse logout() {
        var session = getSession(false);
        if (session != null)
            session.setAttribute("user", null);
        return FrontEndResponse.success();
    }
}