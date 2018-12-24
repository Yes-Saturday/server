package com.ggboy.core.service;

import com.ggboy.common.domain.IPage;
import com.ggboy.core.domain.DO.BlogInfoDO;
import com.ggboy.core.domain.DO.BlogListDO;
import com.ggboy.core.domain.query.BlogListQuery;
import com.ggboy.core.domain.query.BlogShowQuery;
import com.ggboy.core.mapper.BlogMapper;
import com.ggboy.core.mapper.LinkBlogMapper;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BlogService {
    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private LinkBlogMapper linkBlogMapper;

    public Page<BlogListDO> queryList(BlogListQuery query) {
        try {
            Page<BlogListDO> page = IPage.startPage(query.getIPage());
            blogMapper.selectList(query);
            return page;
        } finally {
            IPage.clearPage();
        }
    }

    public BlogInfoDO queryForShow(String id) {
        return blogMapper.selectForShow(new BlogShowQuery(id));
    }

    public List<Map<String, Object>> queryLinkBlog(String id) {
        return linkBlogMapper.selectLinkBlog(id);
    }

    public Integer update(Map<String, Object> params) {
        return blogMapper.update(params);
    }

    private final static IPage getPage(Map<String, Object> params){
        var iPage = params.get("iPage");
        if (iPage instanceof String)
            return new IPage((String) iPage);
        if (iPage instanceof IPage)
            return (IPage) iPage;
        return null;
    }
}
