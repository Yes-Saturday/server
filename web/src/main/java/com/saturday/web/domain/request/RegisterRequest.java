package com.saturday.web.domain.request;

import com.saturday.common.annotation.Name;
import com.saturday.common.annotation.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    @NotNull
    @Name("登录名")
    private String loginNumber;
    @NotNull
    @Name("密码")
    private String password;
    @Name("昵称")
    private String userName;
}
