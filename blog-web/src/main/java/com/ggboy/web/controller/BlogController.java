package com.ggboy.web.controller;

import com.ggboy.common.constant.PropertiesConstant;
import com.ggboy.common.domain.FrontEndResponse;
import com.ggboy.common.domain.IPage;
import com.ggboy.core.domain.query.BlogListQuery;
import com.ggboy.core.enums.BlogOrderBy;
import com.ggboy.core.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/list")
    public FrontEndResponse blog() {
        var blogListQuery = new BlogListQuery();
        blogListQuery.setOrderBy(new String[] {BlogOrderBy.View.desc()});
        blogListQuery.setiPage(new IPage("1", PropertiesConstant.getDefaultBlogListPageSize()));
        var blogList = blogService.queryList(blogListQuery);
        return FrontEndResponse.success(blogList);
    }
}