package com.saturday.web.domain.request;

import com.saturday.common.annotation.Length;
import com.saturday.common.annotation.MaxLength;
import com.saturday.common.annotation.Name;
import com.saturday.common.annotation.NotNull;
import com.saturday.common.utils.StringUtil;

public class UpdateBlogRequest {
    @NotNull
    @Length(11)
    @Name("ID")
    private String id;
    @NotNull
    @MaxLength(32)
    @Name("标题")
    private String title;
    @MaxLength(255)
    @Name("封面")
    private String headImg;
    @NotNull
    @Name("内容")
    private String content;
    @Name("标签")
    @MaxLength(32)
    private String[] tags;

    public String getId() {
        if (StringUtil.isEmpty(id))
            return null;
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        if (StringUtil.isEmpty(title))
            return null;
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHeadImg() {
        if (StringUtil.isEmpty(headImg))
            return null;
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getContent() {
        if (StringUtil.isEmpty(content))
            return null;
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }
}
