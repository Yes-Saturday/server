package com.saturday.blog.domain.query;

import com.saturday.common.query.Query;

public class BlogListQuery extends Query {

    private String categoryId;
    private String tag;
    private String status;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String[] getColumns() {
        return new String[] {
                "blog_basics.blog_id as id",
                "blog_basics.title as title",
                "DATE_FORMAT(blog_basics.create_time,'%Y-%m-%d') as time"
        };
    }
}
