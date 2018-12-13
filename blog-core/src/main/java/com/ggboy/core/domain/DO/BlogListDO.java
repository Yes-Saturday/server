package com.ggboy.core.domain.DO;

public class BlogListDO {
    private String blogId;
    private String title;
    private String synopsis;
    private String synopsisImg;
    private String publisherId;
    private String publisherName;

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

    public String getSynopsisImg() {
        return synopsisImg;
    }

    public void setSynopsisImg(String synopsisImg) {
        this.synopsisImg = synopsisImg;
    }

    public String getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }
}
