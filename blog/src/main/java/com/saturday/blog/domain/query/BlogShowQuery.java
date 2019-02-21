package com.saturday.blog.domain.query;

public class BlogShowQuery extends BaseBlogQuery {

    public BlogShowQuery (String id) {
        super(id, "pass");
    }

    @Override
    public String[] getColumns() {
        return new String[] {
                "blog_basics.title as title",
                "blog_basics.head_img as headImg",
                "cast(blog_basics.content as char CHARACTER SET utf8) as content",
                "DATE_FORMAT(blog_basics.create_time,'%Y-%m-%d') as createTime"};
    }
}