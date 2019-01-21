package com.ggboy.core.domain.query;

import com.ggboy.common.query.Query;

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
                "blog.blog_id as id",
                "blog.title as title",
                "DATE_FORMAT(create_time,'%Y-%m-%d') as time"
        };
    }
}
