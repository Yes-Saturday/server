package com.ggboy.core.mapper;

import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface CategoryMapper {
    String columns = "category.category_id as id,category.name as name";
    String c_table = "category";
    String cd_table = "category_detail cd";

    @Select("SELECT " + columns + " from " + c_table + " where category.status = 'pass' and category.parent_id is null")
    List<Map<String, Object>> selectParentCategory();
}