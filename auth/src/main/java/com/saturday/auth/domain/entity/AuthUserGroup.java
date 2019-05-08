package com.saturday.auth.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Table(name = "auth_user_group")
public class AuthUserGroup {
    @Column(name = "user_id")
    private String userId;
    @Column(name = "group_id")
    private String groupId;
}