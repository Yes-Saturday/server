package com.saturday.auth.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Table(name = "auth_basics")
public class AuthBasics {
    @Column(name = "id")
    private String id;
    @Column(name = "auth_code")
    private String authCode;
}
