package com.saturday.common.domain;

import com.saturday.common.constant.PropertiesConstant;
import com.saturday.common.utils.StringUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

public class IPage {
    private Integer currentPage;
    private Integer pageSize;
    private Boolean isCount;

    public IPage(String page) {
        if (StringUtil.isEmpty(page)) {
            this.pageSize = 0;
            this.currentPage = 0;
            this.isCount = false;
            return;
        }

        try {
            currentPage = Integer.valueOf(page);
        } catch (NumberFormatException e) {
            currentPage = 1;
        }
        this.pageSize = PropertiesConstant.getDefaultPageSize();
        this.isCount = true;
    }

    public IPage(String page, Integer pageSize) {
        if (StringUtil.isEmpty(page)) {
            this.currentPage = 0;
            this.pageSize = 0;
            this.isCount = false;
            return;
        }

        try {
            currentPage = Integer.valueOf(page);
        } catch (NumberFormatException e) {
            currentPage = 1;
        }
        this.pageSize = pageSize != null && pageSize > 0 ? pageSize : PropertiesConstant.getDefaultPageSize();
        this.isCount = true;
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
        return PageHelper.startPage(iPage.currentPage, iPage.pageSize, iPage.isCount, true, null);
    }

    public final static void clearPage() {
        PageHelper.clearPage();
    }
}
