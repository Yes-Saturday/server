package com.saturday.web.controller.auth;

import com.saturday.auth.service.AuthService;
import com.saturday.common.annotation.Auth;
import com.saturday.common.annotation.Verify;
import com.saturday.common.domain.FrontResponse;
import com.saturday.common.exception.BusinessException;
import com.saturday.common.utils.BaseRSA;
import com.saturday.common.utils.CacheUtil;
import com.saturday.common.utils.PasswordHandler;
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
@RequestMapping("/auth")
public class AuthController extends BaseController {

    @Autowired
    private AuthService authService;

//    @GetMapping("/typeAuth")
//    @Auth(name = "类型权限", value = "type_auth")
//    public Object typeAuth() {
//        var typeAuth = authService.getAllTypeAuth();
//        return FrontResponse.success(typeAuth);
//    }
//
//    @GetMapping("/methodAuth")
//    @Auth(name = "方法权限", value = "method_auth")
//    public Object methodAuth(String code) {
//        var methodAuth = authService.getMethodAuthByTypeCode(code);
//        return FrontResponse.success(methodAuth);
//    }
}