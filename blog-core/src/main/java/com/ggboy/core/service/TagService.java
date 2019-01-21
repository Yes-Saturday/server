package com.ggboy.core.service;

import com.ggboy.common.utils.ArrayUtil;
import com.ggboy.common.utils.StringUtil;
import com.ggboy.core.domain.DO.TagDO;
import com.ggboy.core.mapper.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TagService {

    @Autowired
    private TagMapper tagMapper;

    public List<TagDO> getTagsAndCount() {
        return tagMapper.selectTagsAndCount();
    }

    @Transactional
    public void updateIdTags(String id, String... tags) {
        tagMapper.deleteTags(id);
        addIdTags(id, tags);
    }

    public void addIdTags(String id, String... tags) {
        if (tags != null && tags.length > 0)
            tagMapper.insertTags(id, removeRepeatAndEmpty(tags));
    }

    private String[] removeRepeatAndEmpty(String[] data) {
        return ArrayUtil.removeRepeat(data, StringUtil::isEmpty);
    }
}