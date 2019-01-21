package com.ggboy.core.domain.query;

public class BlogUpdateQuery extends BaseBlogQuery {

    public BlogUpdateQuery(String id) {
        super(id);
    }

    @Override
    public String[] getColumns() {
        return new String[] {
                "blog.blog_id as id",
                "blog.title as title",
                "blog.head_img as headImg",
                "cast(blog.content as char CHARACTER SET utf8) as content",
                "DATE_FORMAT(create_time,'%Y-%m-%d') as createTime",
                "(select group_concat(tag.tag_name separator ';') from tag where tag.id = blog_id) as tags"
    };
    }
}
