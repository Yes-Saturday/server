package com.saturday.blog.service;

import com.saturday.blog.domain.entry.BlogTag;
import com.saturday.blog.mapper.BlogTagMapper;
import com.saturday.common.utils.ListUtil;
import com.saturday.common.utils.StringUtil;
import com.saturday.blog.domain.entry.TagCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BlogTagService {

    @Autowired
    private BlogTagMapper blogTagMapper;

    public List<TagCount> getTagsAndCount() {
        return blogTagMapper.selectTagsAndCount();
    }

    @Transactional
    public void updateIdTags(String blogId, List<String> tags) {
        blogTagMapper.delete(new BlogTag(){{setBlogId(blogId);}});
        addIdTags(blogId, tags);
    }

    @Transactional
    public void addIdTags(String blogId, List<String> tags) {
        for (var tag : ListUtil.removeRepeat(tags, StringUtil::isEmpty))
            blogTagMapper.insert(new BlogTag(){{setBlogId(blogId);setTagName(tag);}});
    }

    public List<BlogTag> getTags(BlogTag blogTag) {
        return blogTagMapper.select(blogTag);
    }

    public List<BlogTag> getTagsByBlogId(String blogId) {
        return getTags(new BlogTag(){{setBlogId(blogId);}});
    }
}