package com.saturday.system.mapper;

import org.apache.ibatis.annotations.*;

import java.util.Map;

@Mapper
public interface PublisherMapper {
    String table = "blog_publisher";

    @Select("select publisher_id as id,name from " + table + " where login_name = #{loginName,jdbcType=VARCHAR} and password = #{pwd,jdbcType=VARCHAR} and status = 'pass'")
    Map<String, Object> select4Login(@Param("loginName") String loginName, @Param("pwd") String pwd);
}