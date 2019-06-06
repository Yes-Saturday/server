package com.saturday.web.controller.user;

import com.saturday.common.annotation.Auth;
import com.saturday.common.annotation.Verify;
import com.saturday.common.domain.FrontResponse;
import com.saturday.common.exception.BusinessException;
import com.saturday.common.utils.BaseRSA;
import com.saturday.common.utils.CacheUtil;
import com.saturday.common.utils.PasswordHandler;
import com.saturday.common.utils.UuidUtil;
import com.saturday.sequence.enums.SequenceName;
import com.saturday.sequence.service.SequenceService;
import com.saturday.system.service.RsaService;
import com.saturday.user.domain.entity.UserBasics;
import com.saturday.user.service.UserService;
import com.saturday.web.controller.BaseController;
import com.saturday.web.domain.request.LoginRequest;
import com.saturday.web.domain.request.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    private final static String LOGIN_ERROR_KEY = "LOGIN_ERROR#>";

    @Autowired
    private UserService userService;
    @Autowired
    private SequenceService sequenceService;
    @Autowired
    private RsaService rsaService;

    @PostMapping("/register")
    @Auth(name = "创建用户", value = "create_user")
    public Object create(@Verify RegisterRequest registerRequest) {
        var userBasics = userService.queryUserByLoginNumber(registerRequest.getLoginNumber());

        if (userBasics != null)
            throw new BusinessException("用户已存在");

        var userId = sequenceService.next(SequenceName.UserId);
        String pwd = PasswordHandler.getPwd(registerRequest.getPassword());

        userService.createUser(userId, registerRequest.getLoginNumber(), registerRequest.getUserName(), pwd);
        return FrontResponse.success();
    }

    @PostMapping("/login")
    public Object login(@Verify LoginRequest loginRequest) {
        // TODO
        Object loginError = CacheUtil.get(buildLoginErrorKey(loginRequest.getLoginNumber()));
        if (loginError != null)
            throw new BusinessException("901", "登陆频繁");

        byte[] privateKey = rsaService.getPrivateKey(getSessionId());
        if (privateKey == null)
            throw new BusinessException("902", "公钥过期");

        String pwd = loginRequest.getPassword();
        try {
            pwd = PasswordHandler.getPwd(BaseRSA.decryptByPrivateKey(Base64Utils.decodeFromString(pwd), privateKey));
        } catch (Exception e) {
            throw new BusinessException("903", "RSA解密失败");
        }

        var userBasics = userService.queryUser(loginRequest.getLoginNumber(), pwd);

        if (userBasics == null) {
            // TODO
            CacheUtil.put(buildLoginErrorKey(loginRequest.getLoginNumber()), "", 5 * 60);
            throw new BusinessException("904", "用户名或密码错误");
        }

        doLogin(userBasics);
        return FrontResponse.success();
    }

    @GetMapping("/logout")
    public Object logout() {
        doLogout();
        return FrontResponse.success();
    }

    @GetMapping("/getLoginUser")
    public Object getLoginUser() {
        var user = getSessionAttribute("user");
        return FrontResponse.success(user);
    }

    private String buildLoginErrorKey(String loginNumber) {
        return LOGIN_ERROR_KEY + loginNumber;
    }

    private void doLogin(UserBasics userBasics) {
        setSessionAttribute("user", userBasics);
        getSession().setMaxInactiveInterval(3600);
    }

    private void doLogout() {
        var session = getSession(false);
        if (session != null)
            session.setAttribute("user", null);
    }
}