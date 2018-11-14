package com.ggboy.core.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class Provider {
    private static final String blog_table = "blog tb";

    public String queryBlogList(final Map<String, Object> params) {
        return new SQL() {{
            SELECT("tb.blog_id");
            SELECT("tb.title");
            SELECT("tb.synopsis");
            FROM(blog_table);
            if (params.get("category_id") != null) {
                INNER_JOIN("category_detail tcd on tcd.blog_id = tb.blog_id");
                WHERE("tcd.category_id = #{category_id,jdbcType=VARCHAR}");
            }
            WHERE("tb.status = 'pass'");

            Object orderBy = params.get("orderBy");
            if (orderBy != null)
                if (orderBy.getClass().isArray())
                    for (Object item : (Object[]) orderBy)
                        ORDER_BY(item.toString());
                else
                    ORDER_BY(orderBy.toString());
        }}.toString();
    }
}