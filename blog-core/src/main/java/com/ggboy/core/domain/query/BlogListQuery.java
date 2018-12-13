package com.ggboy.core.domain.query;

import com.ggboy.common.query.Query;

public class BlogListQuery extends Query {

    private String categoryId;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String[] getColumns() {
        return new String[] {
                "blog.blog_id as blogId",
                "blog.title as title",
                "blog.synopsis as synopsis",
                "blog.synopsis_img as synopsisImg",
                "blog_publisher.publisher_id as publisherId",
                "blog_publisher.name as publisherName"
        };
    }
}
