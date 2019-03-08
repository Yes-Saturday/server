package com.saturday.user.service;

import com.saturday.user.domain.entity.UserBasics;
import com.saturday.user.domain.entity.UserSecurity;
import com.saturday.user.mapper.UserBasicsMapper;
import com.saturday.user.mapper.UserSecurityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserSecurityMapper userSecurityMapper;
    @Autowired
    private UserBasicsMapper userBasicsMapper;

    @Transactional
    public boolean createUser(String userId, String loginNumber, String pwd) {
        var userBasics = new UserBasics();
        userBasics.setUserId();
        userBasicsMapper.insert()
        var userSecurity = new UserSecurity();
        userSecurity.setUserId(userId);
        userSecurity.setLoginNumber(loginNumber);
        userSecurity.setPwd(pwd);
        return userSecurityMapper.insert(userSecurity) > 0;
    }

    public UserSecurity queryUser(String loginNumber, String pwd) {
        var userSecurity = new UserSecurity();
        userSecurity.setLoginNumber(loginNumber);
        userSecurity.setPwd(pwd);
        return userSecurityMapper.selectOne(userSecurity);
    }

    public UserSecurity queryUser(String userId) {
        return userSecurityMapper.selectByPrimaryKey(userId);
    }

    public boolean changePassword(String userId, String pwd, String newPwd) {
        return userSecurityMapper.updatePwdByUserIdAndOldPwd(userId, pwd, newPwd) > 0;
    }

    public boolean changePassword(String userId, String newPwd) {
        var userSecurity = new UserSecurity();
        userSecurity.setUserId(userId);
        userSecurity.setPwd(newPwd);
        return userSecurityMapper.updateByPrimaryKey(userSecurity) > 0;
    }
}