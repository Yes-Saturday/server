package com.ggboy.web.domain.request;

import com.ggboy.common.annotation.MaxLength;
import com.ggboy.common.annotation.Name;
import com.ggboy.common.annotation.NotNull;
import com.ggboy.common.utils.StringUtil;

public class CreateBlogRequest {
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
}
