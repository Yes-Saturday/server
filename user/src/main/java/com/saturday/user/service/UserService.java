package com.saturday.user.service;

import com.saturday.common.exception.InternalException;
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

    @Transactional(rollbackFor = Exception.class)
    public void createUser(String userId, String loginNumber, String userName, String pwd) {
        var userBasics = new UserBasics();
        userBasics.setUserId(userId);
        userBasics.setUserName(userName);
        boolean result = userBasicsMapper.insert(userBasics) > 0;

        if (result) {
            var userSecurity = new UserSecurity();
            userSecurity.setUserId(userId);
            userSecurity.setLoginNumber(loginNumber);
            userSecurity.setPwd(pwd);
            result = userSecurityMapper.insert(userSecurity) > 0;
        }

        if (!result)
            throw new InternalException("数据库插入失败");
    }

    public UserBasics queryUser(String loginNumber, String pwd) {
        var userSecurity = new UserSecurity();
        userSecurity.setLoginNumber(loginNumber);
        userSecurity.setPwd(pwd);
        userSecurity = userSecurityMapper.selectOne(userSecurity);

        if (userSecurity == null)
            return null;
        return userBasicsMapper.selectByPrimaryKey(userSecurity.getUserId());
    }

    public UserBasics queryUserByLoginNumber(String loginNumber) {
        return queryUser(loginNumber, null);
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