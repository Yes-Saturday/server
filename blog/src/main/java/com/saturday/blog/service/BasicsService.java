package com.saturday.blog.service;

import com.saturday.blog.domain.query.*;
import com.saturday.common.domain.IPage;
import com.saturday.common.utils.StringUtil;
import com.saturday.blog.domain.DO.BasicsDO;
import com.saturday.blog.domain.DO.BlogListDO;
import com.saturday.blog.mapper.BlogMapper;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BasicsService {
    @Autowired
    private BlogMapper blogMapper;

    public Page<BlogListDO> queryList(BlogListQuery query) {
        try {
            Page<BlogListDO> page = IPage.startPage(query.getIPage());
            blogMapper.selectBlogList(query);
            return page;
        } finally {
            IPage.clearPage();
        }
    }

    public BasicsDO queryForShow(String id) {
        return blogMapper.selectBlogByIdAndStatus(new BlogShowQuery(id));
    }

    public BasicsDO queryForUpdate(String id) {
        return blogMapper.selectBlogByIdAndStatus(new BlogUpdateQuery(id));
    }

    public boolean createBlog(String id, String headImg, String title, String content) {
        var blogInsertDbreq = new BlogInsertDbreq();
        blogInsertDbreq.setBlogId(id);
        blogInsertDbreq.setHeadImg(headImg);
        blogInsertDbreq.setTitle(title);
        blogInsertDbreq.setContent(StringUtil.toBytes(content));
        blogInsertDbreq.setStatus("pass");
        return blogMapper.insertBasics(blogInsertDbreq) > 0;
    }

    public boolean updateBlog(String id, String headImg, String title, String content) {
        var blogUpdateDbreq = new BlogUpdateDbreq();
        blogUpdateDbreq.setId(id);
        blogUpdateDbreq.setHeadImg(headImg);
        blogUpdateDbreq.setTitle(title);
        blogUpdateDbreq.setContent(StringUtil.toBytes(content));
        return blogMapper.updateBlog(blogUpdateDbreq) > 0;
    }
}
