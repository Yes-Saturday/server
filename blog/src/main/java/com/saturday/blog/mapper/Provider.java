package com.saturday.blog.mapper;

import com.saturday.blog.domain.query.BaseBlogQuery;
import com.saturday.blog.domain.query.BlogListQuery;
import org.apache.ibatis.jdbc.SQL;

public class Provider {
    public String queryBlogList(final BlogListQuery query) {
        return new SQL() {{
            SELECT_DISTINCT(query.getColumns());
            FROM("blog_basics");
            if (query.getCategoryId() != null)
                WHERE("blog_basics.category_id = #{categoryId,jdbcType=VARCHAR}");
            if (query.getStatus() != null)
                WHERE("blog_basics.status = #{status,jdbcType=VARCHAR}");
            if (query.getTag() != null)
                WHERE("exists(select 1 from blog_tag where blog_tag.tag_name = #{tag,jdbcType=VARCHAR} and blog_tag.id = blog_basics.blog_id)");
        }}.toString();
    }

    public String queryBlogByIdAndStatus(final BaseBlogQuery query) {
        return new SQL() {{
            SELECT_DISTINCT(query.getColumns());
            FROM("blog_basics");
            WHERE("blog_basics.blog_id = #{id,jdbcType=VARCHAR}");
            if (query.getStatus() != null)
                WHERE("blog_basics.status = #{status,jdbcType=VARCHAR}");
        }}.toString();
    }
}