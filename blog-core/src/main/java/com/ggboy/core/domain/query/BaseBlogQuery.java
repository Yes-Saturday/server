package com.ggboy.core.domain.query;

import com.ggboy.common.query.Query;

public abstract class BaseBlogQuery extends Query {

    private String id;
    private String status;

    public BaseBlogQuery(String id) {
        this.id = id;
    }

    public BaseBlogQuery(String id, String status) {
        this.id = id;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }
}
