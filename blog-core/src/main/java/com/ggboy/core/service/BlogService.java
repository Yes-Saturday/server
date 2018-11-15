package com.ggboy.core.service;

import com.ggboy.common.domain.IPage;
import com.ggboy.core.enums.BlogOrderBy;
import com.ggboy.core.mapper.BlogMapper;
import com.ggboy.core.mapper.LinkBlogMapper;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BlogService {
    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private LinkBlogMapper linkBlogMapper;

    public Page<Map<String, Object>> queryList(Map<String, Object> params, IPage iPage) {
        try {
            Page<Map<String, Object>> page = IPage.startPage(iPage);
            blogMapper.selectList(params);
            return page;
        } finally {
            IPage.clearPage();
        }
    }

    public Page<Map<String, Object>> querySimpleList(String orderBy, IPage iPage) {
        try {
            Page<Map<String, Object>> page = IPage.startPage(iPage);
            blogMapper.selectSimpleList(orderBy);
            return page;
        } finally {
            IPage.clearPage();
        }
    }

    public Page<Map<String, Object>> queryTimeLine(IPage iPage) {
        try {
            Page<Map<String, Object>> page = IPage.startPage(iPage);
            blogMapper.selectTimeLine();
            return page;
        } finally {
            IPage.clearPage();
        }
    }

    public List<Map<String, Object>> queryTop() {
        var tops = new ArrayList<Map<String, Object>>(3);
        var result = blogMapper.selectTop(BlogOrderBy.Time.desc());
        result.put("memo", "最新发布");
        tops.add(result);
        result = blogMapper.selectTop(BlogOrderBy.View.desc());
        result.put("memo", "最受欢迎");
        tops.add(result);
        result = blogMapper.selectTop(BlogOrderBy.Weight.desc());
        result.put("memo", "重磅推荐");
        tops.add(result);
        return tops;
    }

    public List<Map<String, Object>> queryLinkBlog(String id) {
        return linkBlogMapper.selectLinkBlog(id);
    }

    public Map<String, Object> queryBlogDetail(String id) {
        return blogMapper.selectOne(id);
    }

    public Integer viewPlusOne(String id) {
        return blogMapper.viewPlusOne(id);
    }

    public Integer favoritePlusOne(String id) {
        return blogMapper.favoritePlusOne(id);
    }
}
