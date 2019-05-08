package com.saturday.web.controller.blog;

import com.saturday.blog.domain.query.BlogListQuery;
import com.saturday.blog.enums.BlogOrderBy;
import com.saturday.blog.service.BlogBasicsService;
import com.saturday.blog.service.BlogTagService;
import com.saturday.common.domain.FrontResponse;
import com.saturday.common.domain.IPage;
import com.saturday.common.exception._404Exception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    private BlogTagService blogTagService;
    @Autowired
    private BlogBasicsService blogBasicsService;

    @GetMapping("/tags")
    public Object tags() {
        var result = blogTagService.getTagsAndCount();
        return FrontResponse.success(result);
    }

    @GetMapping("/tag/{tag}")
    public Object tag(@PathVariable String tag) {
        var list = blogBasicsService.queryList(new BlogListQuery() {{
            setTag(tag);
            setIPage(new IPage(BlogOrderBy.Weight.desc()));
            setStatus("pass");
        }});

        if (list == null || list.size() == 0)
            throw _404Exception.DEFAULT_404;

        return list;
    }
}
