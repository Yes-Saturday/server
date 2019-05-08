package com.saturday.blog.domain.entry;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

@Getter
@Setter
@Table(name = "blog_basics")
public class BlogBasics {
    @Id
    @Column(name = "blog_id")
    private String blogId;
    @Column(name = "title")
    private String title;
    @Column(name = "head_img")
    private String headImg;
    @Column(name = "content")
    private byte[] content;
    @Column(name = "view_count")
    private Integer viewCount;
    @Column(name = "category_id")
    private String categoryId;
    @Column(name = "status")
    private String status;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "modify_time")
    private Date modifyTime;
    @Column(name = "weight")
    private BigDecimal weight;

    public BlogBasics() {}

    public BlogBasics(String blogId, boolean forInsert) {
        this.blogId = blogId;
        if (forInsert) {
            this.createTime = new Date();
            this.modifyTime = createTime;
            this.viewCount = 0;
        } else
            modifyTime = new Date();
    }

    public BigDecimal getWeight() {
        if (weight == null || weight.compareTo(BigDecimal.ZERO) < 1)
            return BigDecimal.ZERO;
        if (weight.compareTo(BigDecimal.TEN) > -1)
            return BigDecimal.TEN;
        return weight.setScale(3, RoundingMode.DOWN);
    }
}
