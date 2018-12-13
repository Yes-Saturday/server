package com.ggboy.core.mapper;

import com.ggboy.core.domain.query.BlogListQuery;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class Provider {
    public String queryBlogList(final BlogListQuery query) {
        return new SQL() {{
            SELECT_DISTINCT(query.getColumns());
            FROM("blog");
            INNER_JOIN("blog_publisher ON blog_publisher.publisher_id = blog.publisher_id ");
            if (query.getCategoryId() != null) {
                INNER_JOIN("category_detail on category_detail.blog_id = blog.blog_id");
                INNER_JOIN("category ON category.category_id = category_detail.category_id ");
                WHERE("category.category_id = #{categoryId,jdbcType=VARCHAR}");
                OR();
                WHERE("category.parent_id = #{categoryId,jdbcType=VARCHAR}");
            }
            WHERE("blog.status = 'pass'");

            String[] orderBy = query.getOrderBy();
            if (orderBy != null)
                for (String item : orderBy)
                    ORDER_BY(item);
        }}.toString();
    }

    public String updateBlog(final Map<String, Object> params) {
        return new SQL() {{
            UPDATE("blog");
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