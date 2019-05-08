package com.saturday.web.controller;

import com.saturday.blog.domain.entry.BlogBasics;
import com.saturday.blog.service.BlogBasicsService;
import com.saturday.common.annotation.Verify;
import com.saturday.common.utils.StringUtil;
import com.saturday.sequence.enums.SequenceName;
import com.saturday.sequence.service.SequenceService;
import com.saturday.web.domain.request.CreateBlogRequest;
import com.saturday.web.domain.request.UpdateBlogRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/console")
public class ConsoleController {

    @Autowired
    private BlogBasicsService blogService;
    @Autowired
    private SequenceService sequenceService;

    @PostMapping("/createBlog")
    public Object createBlog(@Verify CreateBlogRequest createBlogRequest) {
        String blogId = sequenceService.next(SequenceName.BlogId);

        var blogBasics = new BlogBasics(blogId, true);
        blogBasics.setHeadImg(createBlogRequest.getHeadImg());
        blogBasics.setTitle(createBlogRequest.getTitle());
        blogBasics.setContent(StringUtil.toBytes(createBlogRequest.getContent()));
        blogBasics.setStatus("pass");

        blogService.createBlog(blogBasics, createBlogRequest.getTags());
        return blogId;
    }

    @PostMapping("/updateBlog")
    public void updateBlog(@Verify UpdateBlogRequest updateBlogRequest) {
//        var blogUpdateDbreq = new BlogUpdateDbreq();
//        blogUpdateDbreq.setId(updateBlogRequest.getId());
//        blogUpdateDbreq.setHeadImg(updateBlogRequest.getHeadImg());
//        blogUpdateDbreq.setTitle(updateBlogRequest.getTitle());
//        blogUpdateDbreq.setContent(StringUtil.toBytes(updateBlogRequest.getContent()));
//
//        blogService.updateBlog(blogUpdateDbreq, updateBlogRequest.getTags());
//
//        return FrontResponse.success(updateBlogRequest.getId());
    }
}