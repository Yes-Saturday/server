package com.saturday.blog.service;

import com.github.pagehelper.Page;
import com.saturday.blog.domain.DO.BlogListDO;
import com.saturday.blog.domain.entry.BlogBasics;
import com.saturday.blog.domain.query.BlogListQuery;
import com.saturday.blog.mapper.BlogBasicsMapper;
import com.saturday.common.domain.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BlogBasicsService {
    @Autowired
    private BlogTagService blogTagService;
    @Autowired
    private BlogBasicsMapper blogBasicsMapper;

    public Page<BlogListDO> queryList(BlogListQuery query) {
        try {
            Page<BlogListDO> page = IPage.startPage(query.getIPage());
            blogBasicsMapper.selectBlogList(query);
            return page;
        } finally {
            IPage.clearPage();
        }
    }

    public BlogBasics queryOne(BlogBasics blogBasics) {
        return blogBasicsMapper.selectOne(blogBasics);
    }

    public BlogBasics queryById(String blogId) {
        return blogBasicsMapper.selectOne(new BlogBasics(){{setBlogId(blogId);}});
    }

    @Transactional
    public boolean createBlog(BlogBasics blogBasics, List<String> tags) {
        boolean result = blogBasicsMapper.insert(blogBasics) > 0;
        if (result)
            blogTagService.addIdTags(blogBasics.getBlogId(), tags);
        return result;
    }

    public boolean createBlog(BlogBasics blogBasics) {
        return createBlog(blogBasics, null);
    }

    @Transactional
    public boolean updateBlog(BlogBasics blogBasics, List<String> tags) {
        boolean result = blogBasicsMapper.updateByPrimaryKey(blogBasics) > 0;
        if (result)
            blogTagService.updateIdTags(blogBasics.getBlogId(), tags);
        return result;
    }
}
