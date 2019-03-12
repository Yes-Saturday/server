package com.saturday.web.domain.request;

import com.saturday.common.annotation.Length;
import com.saturday.common.annotation.Name;
import com.saturday.common.annotation.NotNull;
import com.saturday.common.utils.StringUtil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateBlogRequest {
    @NotNull
    @Length(11)
    @Name("ID")
    private String id;
    @NotNull
    @Name("标题")
    private String title;
    @Name("封面")
    private String headImg;
    @NotNull
    @Name("内容")
    private String content;
    @Name("标签")
    private String[] tags;

    public String getId() {
        if (StringUtil.isEmpty(id))
            return null;
        return id;
    }

    public String getTitle() {
        if (StringUtil.isEmpty(title))
            return null;
        return title;
    }

    public String getHeadImg() {
        if (StringUtil.isEmpty(headImg))
            return null;
        return headImg;
    }

    public String getContent() {
        if (StringUtil.isEmpty(content))
            return null;
        return content;
    }
}
