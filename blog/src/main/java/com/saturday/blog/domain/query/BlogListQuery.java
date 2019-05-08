package com.saturday.blog.domain.query;

import com.saturday.common.query.Query;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlogListQuery extends Query {
    private String categoryId;
    private String tag;
    private String status;

    @Override
    public String[] getColumns() {
        return new String[] {
                "blog_basics.blog_id as id",
                "blog_basics.title as title",
                "DATE_FORMAT(blog_basics.create_time,'%Y-%m-%d') as time"
        };
    }
}
