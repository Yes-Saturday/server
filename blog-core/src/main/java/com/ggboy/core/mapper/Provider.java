package com.ggboy.core.mapper;

import com.ggboy.core.domain.query.BaseBlogQuery;
import com.ggboy.core.domain.query.BlogListQuery;
import com.ggboy.core.domain.query.BlogShowQuery;
import com.ggboy.core.domain.query.BlogUpdateDbreq;
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

    public String queryBlogByIdAndStatus(final BaseBlogQuery query) {
        return new SQL() {{
            SELECT_DISTINCT(query.getColumns());
            FROM("blog");
            WHERE("blog.blog_id = #{id,jdbcType=VARCHAR}");
            if (query.getStatus() != null)
                WHERE("blog.status = #{status,jdbcType=VARCHAR}");
        }}.toString();
    }

    public String updateBlog(final BlogUpdateDbreq dbreq) {
        return new SQL() {{
            UPDATE("blog");
            if (dbreq.getTitle() != null)
                SET("blog.title = #{title}");
            if (dbreq.getContent() != null)
                SET("blog.content = #{content}");
            if (dbreq.getHeadImg() != null)
                SET("blog.head_img = #{headImg}");
            if (dbreq.getStatus() != null)
                SET("blog.status = #{status}");
            if (dbreq.getWeight() != null)
                SET("blog.weight = #{weight}");
            SET("blog.modify_time = now()");
            WHERE("blog.blog_id = #{id}");
        }}.toString();
    }
}