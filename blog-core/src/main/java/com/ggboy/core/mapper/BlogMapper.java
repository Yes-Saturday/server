package com.ggboy.core.mapper;

import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface BlogMapper {
    String columns = "blog_id,title,synopsis";
    String columns_detail = "title,synopsis,cast(content as char CHARACTER SET utf8) as content,view_count,favorite_count,DATE_FORMAT(modify_time,'%Y-%m-%d %H:%i') as time";
    String table = "blog";

    @SelectProvider(type = Provider.class, method = "queryBlogList")
    List<Map<String, Object>> selectList(Map<String, Object> params);

    @Select("select blog_id,title from " + table + " order by ${orderBy}")
    List<Map<String, Object>> selectSimpleList(@Param("orderBy") String orderBy);

    @Select("select blog_id,title,DATE_FORMAT(create_time,'%Y-%m-%d') as time from " + table + " order by create_time")
    List<Map<String, Object>> selectTimeLine();

    @Select("select " + columns + " from " + table + " order by ${orderBy} limit 1")
    Map<String, Object> selectTop(@Param("orderBy") String orderBy);

    @Select("select " + columns_detail + " from " + table + " where status = 'pass' and blog_id = #{blogId, jdbcType=VARCHAR}")
    Map<String, Object> selectOne(@Param("blogId") String blogId);

    @Update("update " + table + " SET view_count = view_count + 1 WHERE blog_id = #{blogId,jdbcType=VARCHAR}")
    Integer viewPlusOne(@Param("blogId") String blogId);

    @Update("update " + table + " SET favorite_count = favorite_count + 1 WHERE blog_id = #{blogId,jdbcType=VARCHAR}")
    Integer favoritePlusOne(@Param("blogId") String blogId);
}