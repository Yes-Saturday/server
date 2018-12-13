package com.ggboy.core.domain.info;

import java.math.BigDecimal;
import java.util.List;

public class BlogInfo {
    private String blogId;
    private String title;
    private String synopsis;
    private byte[] synopsisImg;
    private byte[] content;
    private Integer viewCount;
    private Integer favoriteCount;
    private String time;
    private String status;
    private BigDecimal weight;
    private List<CategoryInfo> categories;

    public String getBlogId() {
        return blogId;
    }

    public void setBlogId(String blogId) {
        this.blogId = blogId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public byte[] getSynopsisImg() {
        return synopsisImg;
    }

    public void setSynopsisImg(byte[] synopsisImg) {
        this.synopsisImg = synopsisImg;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(Integer favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public List<CategoryInfo> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryInfo> categories) {
        this.categories = categories;
    }
}