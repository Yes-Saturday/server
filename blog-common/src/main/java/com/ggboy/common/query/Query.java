package com.ggboy.common.query;

import com.ggboy.common.domain.IPage;

import java.util.Date;

public class Query<T> {
    private T queryInfo;
    private Date startCreateTime;
    private Date endCreateTime;
    private Date startModifyTime;
    private Date endModifyTime;
    private Long startTime;
    private Long endTime;

    public Query () {
    }

    public Query (T queryInfo) {
        this.queryInfo = queryInfo;
    }

    public T getQueryInfo() {
        return queryInfo;
    }

    public void setQueryInfo(T queryInfo) {
        this.queryInfo = queryInfo;
    }

    public Date getStartCreateTime() {
        return startCreateTime;
    }

    public void setStartCreateTime(Date startCreateTime) {
        this.startCreateTime = startCreateTime;
    }

    public Date getEndCreateTime() {
        return endCreateTime;
    }

    public void setEndCreateTime(Date endCreateTime) {
        this.endCreateTime = endCreateTime;
    }

    public Date getStartModifyTime() {
        return startModifyTime;
    }

    public void setStartModifyTime(Date startModifyTime) {
        this.startModifyTime = startModifyTime;
    }

    public Date getEndModifyTime() {
        return endModifyTime;
    }

    public void setEndModifyTime(Date endModifyTime) {
        this.endModifyTime = endModifyTime;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }
}
