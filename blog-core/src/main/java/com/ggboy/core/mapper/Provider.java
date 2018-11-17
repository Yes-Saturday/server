package com.ggboy.core.mapper;

import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class Provider {
    private static final String blog_table = "blog";

    public String queryBlogList(final Map<String, Object> params) {
        return new SQL() {{
            SELECT_DISTINCT("blog.blog_id");
            SELECT_DISTINCT("blog.title");
            SELECT_DISTINCT("blog.synopsis");
            FROM(blog_table);
            if (params.get("category_id") != null) {
                INNER_JOIN("category_detail on category_detail.blog_id = blog.blog_id");
                INNER_JOIN("category ON category.category_id = category_detail.category_id ");
                WHERE("category.category_id = #{category_id,jdbcType=VARCHAR}");
                OR();
                WHERE("category.parent_id = #{category_id,jdbcType=VARCHAR}");
            }
            WHERE("blog.status = 'pass'");

            Object orderBy = params.get("orderBy");
            if (orderBy != null)
                if (orderBy.getClass().isArray())
                    for (Object item : (Object[]) orderBy)
                        ORDER_BY(item.toString());
                else
                    ORDER_BY(orderBy.toString());
        }}.toString();
    }

    public String updateBlog(final Map<String, Object> params) {
        return new SQL() {{
            UPDATE(blog_table);
            if (params.get("title") != null)
                SET("blog.title = #{title}");
            if (params.get("synopsis") != null)
                SET("blog.synopsis = #{synopsis}");
            if (params.get("content") != null)
                SET("blog.content = #{content}");
            if (params.get("status") != null)
                SET("blog.status = #{status}");
            if (params.get("weight") != null)
                SET("blog.weight = #{weight}");
            SET("blog.modify_time = now()");
            WHERE("blog.blog_id = #{blog_id}");
        }}.toString();
    }
}