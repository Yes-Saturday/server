package com.saturday.web.domain.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BlogInfoVO {
    private String blogId;
    private String title;
    private String headImg;
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm", timezone = "GMT+8", locale = "zh")
    private Date createTime;

    public void setContent(byte[] content) {
        this.content = new String(content);
    }
}
