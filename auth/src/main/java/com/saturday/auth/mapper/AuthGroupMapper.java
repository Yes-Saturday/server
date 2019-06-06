package com.saturday.auth.mapper;

import com.saturday.auth.domain.entity.AuthGroup;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

public interface AuthGroupMapper extends Mapper<AuthGroup> {
    @SelectProvider(type = Provider.class, method = "selectAuthGroupMaxRgt")
    int selectAuthGroupMaxRgt(String pid);
    @Update("update auth_group set rgt = rgt + 2 where rgt >= #{arg0};update auth_group set lft = lft + 2 where lft > #{arg0};")
    int addLftAndRgt(int value);
}