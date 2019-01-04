package com.ggboy.core.domain.query;

public class BlogShowQuery extends BaseBlogQuery {

    public BlogShowQuery (String id) {
        super(id, "pass");
    }

    @Override
    public String[] getColumns() {
        return new String[] {
                "blog.title as title",
                "blog.head_img as headImg",
                "cast(blog.content as char CHARACTER SET utf8) as content",
                "DATE_FORMAT(create_time,'%Y-%m-%d') as createTime"
    };
    }
}