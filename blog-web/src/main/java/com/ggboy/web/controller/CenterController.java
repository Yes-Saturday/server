package com.ggboy.web.controller;

import com.ggboy.common.exception._404Exception;
import com.ggboy.core.domain.query.BlogListQuery;
import com.ggboy.core.enums.BlogOrderBy;
import com.ggboy.core.service.BlogService;
import com.ggboy.core.service.CategoryService;
import com.ggboy.core.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CenterController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private TagService tagService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/category")
    public String category(ModelMap modelMap) {
        var categoryList = categoryService.queryList();
        modelMap.put("categoryList", categoryList);
        return "category";
    }

    @GetMapping("/category/{categoryId}")
    public String categoryBlogList(@PathVariable("categoryId") String categoryId, String categoryName, ModelMap modelMap) {
        var list = blogService.queryList(new BlogListQuery(){{
            setCategoryId(categoryId);
            setOrderBy(BlogOrderBy.Weight.desc());
            setStatus("pass");
        }});
        modelMap.put("blogList", list);
        modelMap.put("categoryName", categoryName);
        return "list";
    }

    @GetMapping("/info/{blogId}")
    public String info(@PathVariable("blogId") String blogId, ModelMap modelMap) {
        var blogInfo = blogService.queryForShow(blogId);
        if (blogInfo == null)
            throw new _404Exception();
        modelMap.put("blog", blogInfo);
        return "info";
    }

    @GetMapping("/times")
    public String times(ModelMap modelMap) {
        var list = blogService.queryList(new BlogListQuery(){{setOrderBy(BlogOrderBy.CreateTime.desc());setStatus("pass");}});
        modelMap.put("blogList", list);
        return "times";
    }

    @GetMapping("/tags")
    public String tags(ModelMap modelMap) {
        var tags = tagService.getTagsAndCount();
        modelMap.put("tags", tags);
        return "tags";
    }

    @GetMapping("/tag/{tag}")
    public String tag(@PathVariable String tag, ModelMap modelMap) {
        var list = blogService.queryList(new BlogListQuery(){{
            setTag(tag);
            setOrderBy(BlogOrderBy.Weight.desc());
            setStatus("pass");
        }});

        if (list == null || list.size() == 0)
            throw new _404Exception();

        modelMap.put("blogList", list);
        modelMap.put("tagName", tag);
        return "tag";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}