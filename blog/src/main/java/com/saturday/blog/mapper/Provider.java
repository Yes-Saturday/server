package com.saturday.blog.mapper;

import com.saturday.blog.domain.query.BaseBlogQuery;
import com.saturday.blog.domain.query.BlogListQuery;
import com.saturday.blog.domain.query.BlogUpdateDbreq;
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

            String[] orderBy = query.getOrderBy();
            if (orderBy != null)
                for (String item : orderBy)
                    ORDER_BY(item);
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

    public String updateBlog(final BlogUpdateDbreq dbreq) {
        return new SQL() {{
            UPDATE("blog_basics");
            if (dbreq.getTitle() != null)
                SET("blog_basics.title = #{title}");
            if (dbreq.getContent() != null)
                SET("blog_basics.content = #{content}");
            if (dbreq.getHeadImg() != null)
                SET("blog_basics.head_img = #{headImg}");
            if (dbreq.getStatus() != null)
                SET("blog_basics.status = #{status}");
            if (dbreq.getWeight() != null)
                SET("blog_basics.weight = #{weight}");
            SET("blog_basics.modify_time = now()");
            WHERE("blog_basics.blog_id = #{id}");
        }}.toString();
    }

    public String insertTags(final String id, final String[] tags) {
        if (tags == null || tags.length == 0)
            return null;
        var sql = new StringBuilder("insert into blog_tag (id,tag_name) values ");
        for (int i = 0; i < tags.length; i++)
            sql.append("('").append(id).append("','").append(tags[i]).append("')").append(",");
        return sql.deleteCharAt(sql.length() - 1).toString();
    }
}