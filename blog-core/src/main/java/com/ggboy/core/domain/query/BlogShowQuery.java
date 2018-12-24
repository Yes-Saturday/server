package com.ggboy.core.domain.query;

import com.ggboy.common.query.Query;

public class BlogShowQuery extends Query {

    private String id;

    public BlogShowQuery (String id) {
        this.id = id;
    }

    public String getId() {
        return id;
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
