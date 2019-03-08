package com.saturday.web.controller.user;

import com.saturday.common.annotation.Verify;
import com.saturday.common.convert.SequenceIdConvert;
import com.saturday.common.domain.FrontEndResponse;
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

        userService

        long number = sequenceService.next(SequenceName.UserId);
        String userId = SequenceIdConvert.convertFixedLength("US103", number, 11);

        userService.createUser(userId, registerRequest.getLoginNumber(), registerRequest.getPassword());
        return FrontEndResponse.success();
    }

    @PostMapping("/sign")
    public FrontEndResponse create(String pwd) {
        return FrontEndResponse.success(userService.queryUser("ggboy", "151ADE7117C72F4262BDFE673B5C2ECE"));
    }

}
