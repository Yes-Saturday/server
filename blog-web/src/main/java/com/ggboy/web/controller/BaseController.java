package com.ggboy.web.controller;

import com.ggboy.common.domain.IPage;
import com.ggboy.common.exception.BusinessException;
import com.ggboy.core.enums.BlogOrderBy;
import com.ggboy.core.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;

@Controller
public class BaseController {
    @Autowired
    private BlogService blogService;

    @GetMapping("/")
    public String index(ModelMap param) {
        var queryBlog = new HashMap<String, Object>(2);
        queryBlog.put("orderBy", BlogOrderBy.View.desc());
        var blogList = blogService.queryList(queryBlog, new IPage(1, 6));
        var tops = blogService.queryTop();
        param.put("tops", tops);
        param.put("blogList", blogList);
        return "index";
    }

    @GetMapping("/info/{blogId}")
    public String info(@PathVariable("blogId") String blogId, ModelMap param) {
        var blogDetail = blogService.queryBlogDetail(blogId);
        if (blogDetail == null)
            throw new BusinessException("404", "404 not found");
        param.put("blog", blogDetail);
        return "info";
    }
}