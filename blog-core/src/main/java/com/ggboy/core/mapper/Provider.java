package com.ggboy.core.mapper;

import com.ggboy.core.domain.query.BlogListQuery;
import com.ggboy.core.domain.query.BlogShowQuery;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class Provider {
    public String queryBlogList(final BlogListQuery query) {
        return new SQL() {{
            SELECT_DISTINCT(query.getColumns());
            FROM("blog");
            if (query.getCategoryId() != null)
                WHERE("blog.category_id = #{categoryId,jdbcType=VARCHAR}");
            if (query.getStatus() != null)
                WHERE("blog.status = #{status,jdbcType=VARCHAR}");

            String[] orderBy = query.getOrderBy();
            if (orderBy != null)
                for (String item : orderBy)
                    ORDER_BY(item);
        }}.toString();
    }

    public String queryForShow(final BlogShowQuery query) {
        return new SQL() {{
            SELECT_DISTINCT(query.getColumns());
            FROM("blog");
            WHERE("blog.blog_id = #{id,jdbcType=VARCHAR}");
            WHERE("blog.status = 'pass'");
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