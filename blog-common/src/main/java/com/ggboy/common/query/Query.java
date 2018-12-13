package com.ggboy.common.query;

import com.ggboy.common.domain.IPage;

public abstract class Query {
    private IPage iPage;
    private String[] orderBy;

    public abstract String[] getColumns();

    public IPage getiPage() {
        return iPage;
    }

    public void setiPage(IPage iPage) {
        this.iPage = iPage;
    }

    public String[] getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String[] orderBy) {
        this.orderBy = orderBy;
    }
}