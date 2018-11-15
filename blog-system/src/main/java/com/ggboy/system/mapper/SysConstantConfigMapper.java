package com.ggboy.system.mapper;

import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysConstantConfigMapper {
    String columns = "value";
    String table = "sys_constant_config";

    @Select("select " + columns + " from " + table + " where status = 'pass' and (invalid_time is null or invalid_time < now()) and type = #{type,jdbcType=VARCHAR}")
    List<String> queryList(@Param("type") String type);
}