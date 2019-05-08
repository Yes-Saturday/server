package com.saturday.web.controller.blog;

import com.saturday.blog.domain.entry.BlogTag;
import com.saturday.blog.domain.query.BlogListQuery;
import com.saturday.blog.enums.BlogOrderBy;
import com.saturday.blog.service.BlogBasicsService;
import com.saturday.blog.service.CategoryService;
import com.saturday.blog.service.BlogTagService;
import com.saturday.common.domain.FrontResponse;
import com.saturday.common.domain.IPage;
import com.saturday.common.exception._404Exception;
import com.saturday.web.domain.VO.BlogInfoVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private BlogBasicsService blogBasicsService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BlogTagService blogTagService;

    @GetMapping("/category")
    public String category(ModelMap modelMap) {
        var categoryList = categoryService.queryList();
        modelMap.put("categoryList", categoryList);
        return "category";
    }

    @GetMapping("/category/{categoryId}")
    public String categoryBlogList(@PathVariable("categoryId") String categoryId, String categoryName, ModelMap modelMap) {
        var list = blogBasicsService.queryList(new BlogListQuery() {{
            setCategoryId(categoryId);
            setStatus("pass");
            setIPage(new IPage(BlogOrderBy.Weight.desc()));
        }});
        modelMap.put("blogList", list);
        modelMap.put("categoryName", categoryName);
        return "list";
    }

    @GetMapping("/info/{blogId}")
    public Object info(@PathVariable("blogId") String blogId) {
        var blogInfo = blogBasicsService.queryById(blogId);
        if (blogInfo == null)
            throw _404Exception.DEFAULT_404;
        var blogInfoVO = new BlogInfoVO();
        BeanUtils.copyProperties(blogInfo, blogInfoVO);
        return FrontResponse.success(blogInfoVO);
    }

    @GetMapping("/times")
    public Object times() {
        var list = blogBasicsService.queryList(new BlogListQuery() {{
            setStatus("pass");
            setIPage(new IPage(BlogOrderBy.CreateTime.desc()));
        }});
        return FrontResponse.success(list);
    }

    @GetMapping("/tags/{blogId}")
    public Object blogTags(@PathVariable("blogId") String blogId) {
        var tags = blogTagService.getTags(new BlogTag(){{setBlogId(blogId);}});
        return FrontResponse.success(tags);
    }
}