package com.saturday.common.domain;

import com.saturday.common.utils.StringUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

public class IPage {

    private final static int DEFAULT_PAGE_SIZE = 10;

    private int currentPage = 0;
    private int pageSize = 0;
    private boolean isCount = false;
    private String orderBy;

    public IPage() {
    }

    public IPage(int page) {
        this(null, page, 0);
    }

    public IPage(int page, int pageSize) {
        this(null, page, pageSize);
    }

    public IPage(String orderBy) {
        this(orderBy, 0, 0);
    }

    public IPage(String orderBy, int page) {
        this(orderBy, page, 0);
    }

    public IPage(String orderBy, int page, int pageSize) {
        this.orderBy = orderBy;
        if (page > 0) {
            this.currentPage = page;
            this.pageSize = pageSize > 0 ? pageSize : DEFAULT_PAGE_SIZE;
            this.isCount = true;
        }
    }

    public <E> Page<E> startPage() {
        return startPage(this);
    }

    public void clear() {
        clearPage();
    }

    public final static <E> Page<E> startPage(IPage iPage) {
        if (iPage == null)
            return PageHelper.startPage(0, 0, false, null, null);
        if (!StringUtil.isEmpty(iPage.orderBy))
            PageHelper.orderBy(iPage.orderBy);
        return PageHelper.startPage(iPage.currentPage, iPage.pageSize, iPage.isCount, true, null);
    }

    public final static void clearPage() {
        PageHelper.clearPage();
    }
}
