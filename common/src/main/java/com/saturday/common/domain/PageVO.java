package com.saturday.common.domain;

import com.saturday.common.constant.PropertiesConstant;
import com.github.pagehelper.Page;

public class PageVO {
    private Integer page;
    private Integer pages;
    private Integer startArrow;
    private Integer endArrow;
    private Integer showPageLength;

    public PageVO(int page, int pages, int showPageLength) {
        this.page = page;
        this.pages = pages > 0 ? pages : 1;
        this.showPageLength = showPageLength;
    }

    public PageVO(int page, int pageSize) {
        this(page, pageSize, PropertiesConstant.getDefaultBlogListShowPageButtonSize());
    }

    public PageVO(Page<?> page) {
        this(page.getPageNum(), page.getPages(), PropertiesConstant.getDefaultBlogListShowPageButtonSize());
    }

    public Integer getPage() {
        return this.page;
    }

    public Integer getPages() {
        return this.pages;
    }

    public int getStartArrow() {
        if (this.startArrow == null)
            this.startArrow = pages < showPageLength ? 1 : Math.max(page - (showPageLength >> 1), 1);
        return this.startArrow;
    }

    public int getEndArrow() {
        if (this.endArrow == null) {
            if (this.startArrow == null)
                getStartArrow();
            this.endArrow = Math.min(startArrow + showPageLength, pages);
        }
        return this.endArrow;
    }

    public Integer getShowPageLength() {
        return this.showPageLength;
    }
}
