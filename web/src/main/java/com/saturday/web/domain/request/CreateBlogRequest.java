package com.saturday.web.domain.request;

import com.saturday.common.annotation.Verify.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateBlogRequest {
    @NotEmpty
    @Name("标题")
    private String title;
    @Name("封面")
    private String headImg;
    @NotEmpty
    @Name("内容")
    private String content;
    @Name("标签")
    private List<String> tags;
}
