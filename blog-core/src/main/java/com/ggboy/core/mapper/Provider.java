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
            if (query.getTag() != null)
                WHERE("exists(select 1 from tag where tag.tag_name = #{tag,jdbcType=VARCHAR} and tag.id = blog.blog_id)");

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

    public String insertTags(final String id, final String[] tags) {
        if (tags == null || tags.length == 0)
            return null;
        var sql = new StringBuilder("insert into tag (id,tag_name) values ");
        for (int i = 0; i < tags.length; i++)
            sql.append("('").append(id).append("','").append(tags[i]).append("')").append(",");
        return sql.deleteCharAt(sql.length() - 1).toString();
    }
}