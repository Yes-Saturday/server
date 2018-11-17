package com.ggboy.core.mapper;

import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface CategoryMapper {
    String columns = "c.category_id as category_id,c.name as name";
    String c_table = "category c";
    String cd_table = "category_detail cd";

    @Select("SELECT " + columns + " from " + c_table + " LEFT JOIN " + cd_table + " on c.category_id = cd.category_id where c.status = 'pass' and cd.blog_id = #{blogId,jdbcType=VARCHAR}")
    List<Map<String, Object>> selectCategoryByBlogId(@Param("blogId") String blogId);

    @Select("SELECT " + columns + " from " + c_table + " where c.status = 'pass' and c.parent_id is null")
    List<Map<String, Object>> selectParentCategory();
}