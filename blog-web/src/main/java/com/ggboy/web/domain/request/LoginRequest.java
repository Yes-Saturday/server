package com.ggboy.web.domain.request;

import com.ggboy.common.annotation.Name;
import com.ggboy.common.annotation.NotNull;

public class LoginRequest {
    @NotNull
    @Name("登录名")
    private String loginName;
    @NotNull
    @Name("密码")
    private String password;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
