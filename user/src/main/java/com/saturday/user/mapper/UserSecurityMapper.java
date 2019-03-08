package com.saturday.user.mapper;

import com.saturday.user.domain.entity.UserSecurity;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

public interface UserSecurityMapper extends Mapper<UserSecurity> {
    @Update("update set pwd = #{newPwd} where user_id = #{userId} and pwd = #{pwd}")
    int updatePwdByUserIdAndOldPwd(String userId, String pwd, String newPwd);
}