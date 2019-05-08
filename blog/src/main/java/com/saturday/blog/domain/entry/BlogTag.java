package com.saturday.blog.domain.entry;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Table;

@Getter
@Setter
@Table(name = "blog_tag")
public class BlogTag {
    @Column(name = "blog_id")
    private String blogId;
    @Column(name = "tag_name")
    private String tagName;
}
