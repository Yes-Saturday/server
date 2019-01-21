package com.ggboy.web.controller;

import com.ggboy.common.annotation.Verify;
import com.ggboy.common.convert.SequenceIdConvert;
import com.ggboy.common.domain.FrontEndResponse;
import com.ggboy.common.exception._404Exception;
import com.ggboy.common.utils.JsonUtil;
import com.ggboy.core.service.BlogService;
import com.ggboy.core.service.TagService;
import com.ggboy.system.enums.SequenceName;
import com.ggboy.system.service.SequenceService;
import com.ggboy.web.domain.request.CreateBlogRequest;
import com.ggboy.web.domain.request.UpdateBlogRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/console")
public class ConsoleController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private SequenceService sequenceService;
    @Autowired
    private TagService tagService;

    @GetMapping
    public String console() {
        return "console";
    }

    @GetMapping("/releaseBlog")
    public String releaseBlog(ModelMap modelMap) {
        modelMap.put("option", "release");
        return "blog_editor";
    }

    @GetMapping("/updateBlog/{blogId}")
    public String updateBlog2editor(@PathVariable String blogId, ModelMap modelMap) {
        var blogInfo = blogService.queryForUpdate(blogId);
        if (blogInfo == null)
            throw new _404Exception();
        modelMap.put("blog", blogInfo);
        modelMap.put("option", "update");
        return "blog_editor";
    }

    @PostMapping("/createBlog")
    @ResponseBody
    public FrontEndResponse createBlog(@Verify CreateBlogRequest createBlogRequest) {
        Long id = sequenceService.next(SequenceName.Blog);
        String idStr = SequenceIdConvert.convertFixedLength("BL10", id, 11);
        blogService.createBlog(idStr, createBlogRequest.getHeadImg(), createBlogRequest.getTitle(), createBlogRequest.getContent());
        tagService.addIdTags(idStr, createBlogRequest.getTags());
        return FrontEndResponse.success(idStr);
    }

    @PostMapping("/updateBlog")
    @ResponseBody
    public FrontEndResponse updateBlog(@Verify UpdateBlogRequest updateBlogRequest) {
        blogService.updateBlog(updateBlogRequest.getId(), updateBlogRequest.getHeadImg(), updateBlogRequest.getTitle(), updateBlogRequest.getContent());
        tagService.updateIdTags(updateBlogRequest.getId(), updateBlogRequest.getTags());
        return FrontEndResponse.success(updateBlogRequest.getId());
    }
}