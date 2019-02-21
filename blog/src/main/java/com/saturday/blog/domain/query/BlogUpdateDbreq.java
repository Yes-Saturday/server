package com.saturday.blog.domain.query;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BlogUpdateDbreq {
    private String id;
    private String title;
    private String headImg;
    private byte[] content;
    private String status;
    private BigDecimal weight;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getWeight() {
        if (weight == null)
            return BigDecimal.ZERO;
        if (weight.compareTo(BigDecimal.TEN) >= 1)
            return BigDecimal.TEN;
        return weight.setScale(3, RoundingMode.DOWN);
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }
}
