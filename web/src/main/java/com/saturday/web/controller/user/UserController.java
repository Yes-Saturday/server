package com.saturday.web.controller.user;

import com.saturday.common.annotation.Verify;
import com.saturday.common.convert.SequenceIdConvert;
import com.saturday.common.domain.FrontEndResponse;
import com.saturday.common.exception.BusinessException;
import com.saturday.common.utils.PasswordHandler;
import com.saturday.sequence.enums.SequenceName;
import com.saturday.sequence.service.SequenceService;
import com.saturday.user.service.UserService;
import com.saturday.web.domain.request.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private SequenceService sequenceService;

    @PostMapping("/register")
    public FrontEndResponse create(@Verify RegisterRequest registerRequest) {

        var userBasics = userService.queryUserByLoginNumber(registerRequest.getLoginNumber());

        if (userBasics != null)
            return FrontEndResponse.fail("400", "用户已存在");

        long number = sequenceService.next(SequenceName.UserId);
        String userId = SequenceIdConvert.convertFixedLength("US103", number, 11);
        String pwd = PasswordHandler.getPwd(registerRequest.getPassword());

        userService.createUser(userId, registerRequest.getLoginNumber(), registerRequest.getUserName(), pwd);
        return FrontEndResponse.success();
    }
}
