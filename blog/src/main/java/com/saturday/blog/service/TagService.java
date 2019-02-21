package com.saturday.blog.service;

import com.saturday.blog.mapper.BlogMapper;
import com.saturday.common.utils.ArrayUtil;
import com.saturday.common.utils.StringUtil;
import com.saturday.blog.domain.DO.TagDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TagService {

    @Autowired
    private BlogMapper blogMapper;

    public List<TagDO> getTagsAndCount() {
        return blogMapper.selectTagsAndCount();
    }

    @Transactional
    public void updateIdTags(String id, String... tags) {
        blogMapper.deleteTags(id);
        addIdTags(id, tags);
    }

    public void addIdTags(String id, String... tags) {
        if (tags != null && tags.length > 0)
            blogMapper.insertTags(id, removeRepeatAndEmpty(tags));
    }

    public List<String> getTagsByBlogId(String id) {
        return blogMapper.selectTagsByBlogId(id);
    }

    private String[] removeRepeatAndEmpty(String[] data) {
        return ArrayUtil.removeRepeat(data, StringUtil::isEmpty);
    }
}