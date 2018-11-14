package com.ggboy.core.mapper;

import com.ggboy.common.query.Query;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface BlogMapper {
    String columns = "blog_id,title,synopsis";
    String columns_detail = "title,synopsis,cast(content as char CHARACTER SET utf8) as content,view_count,favorite_count,DATE_FORMAT(modify_time,'%Y-%m-%d') as time";
    String table = "blog";

    @Update("update " + table + " SET current_value = current_value + increment WHERE sequence_name = #{sequenceName,jdbcType=VARCHAR}")
    int update(@Param("sequenceName") String sequenceName);

    @SelectProvider(type = Provider.class, method = "queryBlogList")
    List<Map<String, Object>> selectList(Map<String, Object> params);

    @Select("select " + columns + " from " + table + " order by ${orderBy} limit 1")
    Map<String, Object> selectTop(@Param("orderBy") String orderBy);

    @Select("select " + columns_detail + " from " + table + " where status = 'pass' and blog_id = #{blogId, jdbcType=VARCHAR}")
    Map<String, Object> selectOne(@Param("blogId") String blogId);
}