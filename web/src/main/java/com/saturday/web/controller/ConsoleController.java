package com.saturday.web.controller;

import com.saturday.blog.service.BasicsService;
import com.saturday.blog.service.TagService;
import com.saturday.common.annotation.Verify;
import com.saturday.common.constant.SymbolConstant;
import com.saturday.common.convert.SequenceIdConvert;
import com.saturday.common.domain.FrontEndResponse;
import com.saturday.common.exception._404Exception;
import com.saturday.common.utils.ArrayUtil;
import com.saturday.sequence.enums.SequenceName;
import com.saturday.sequence.service.SequenceService;
import com.saturday.web.domain.request.CreateBlogRequest;
import com.saturday.web.domain.request.UpdateBlogRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/console")
public class ConsoleController {

    @Autowired
    private BasicsService blogService;
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
        var tags = tagService.getTagsByBlogId(blogId);
        modelMap.put("blog", blogInfo);
        modelMap.put("tags", ArrayUtil.toString(tags.toArray(), SymbolConstant.SEMICOLON));
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