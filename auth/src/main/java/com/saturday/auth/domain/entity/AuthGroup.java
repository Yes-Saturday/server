package com.saturday.auth.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Table(name = "auth_group")
public class AuthGroup {
    @Id
    @Column(name = "group_id")
    private String groupId;
    @Column(name = "group_name")
    private String groupName;
    @Column(name = "pid")
    private String pid;
    @Column(name = "lft")
    private Integer lft;
    @Column(name = "rgt")
    private Integer rgt;
}
