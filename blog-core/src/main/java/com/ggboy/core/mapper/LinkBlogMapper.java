package com.ggboy.core.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface LinkBlogMapper {
    String columns = "lb.link_blog_id as blog_id,b.title as title";
    String lb_table = "link_blog lb";
    String b_table = "blog b";

    @Select("SELECT " + columns + " from " + lb_table + " right join " + b_table + " on b.blog_id = lb.link_blog_id where lb.blog_id = #{blogId,jdbcType=VARCHAR}")
    List<Map<String, Object>> selectLinkBlog(@Param("blogId") String blogId);
}