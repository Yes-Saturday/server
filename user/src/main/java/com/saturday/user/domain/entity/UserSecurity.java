package com.saturday.user.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Table(name = "user_security")
public class UserSecurity {
    @Id
    @Column(name = "user_id")
    private String userId;
    @Column(name = "login_number")
    private String loginNumber;
    @Column(name = "pwd")
    private String pwd;
}