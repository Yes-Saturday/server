package com.saturday.sequence.mapper;

import org.apache.ibatis.annotations.*;

import java.util.Map;

@Mapper
public interface SequenceMapper {
    String columns = "current_value as value,increment as inc";
    String table = "sys_sequence";

    @Select("select " + columns + " from " + table + " where sequence_name = #{sequenceName,jdbcType=VARCHAR} for update")
    Map<String, Long> lockSequenceName(@Param("sequenceName") String sequenceName);

    @Update("update " + table + " SET current_value = current_value + increment WHERE sequence_name = #{sequenceName,jdbcType=VARCHAR}")
    int update(@Param("sequenceName") String sequenceName);

    @Insert("insert into " + table + " (sequence_name,current_value,increment) values (#{sequenceName,jdbcType=VARCHAR},0,1)")
    int insert(@Param("sequenceName") String sequenceName);
}