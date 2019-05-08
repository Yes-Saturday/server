package com.saturday.blog.mapper;

import com.saturday.blog.domain.entry.TagCount;
import com.saturday.blog.domain.entry.BlogTag;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BlogTagMapper extends Mapper<BlogTag> {
    String tag_table = "blog_tag";

    @Select("select tag_name as tag,count(0) as count from " + tag_table + " group by tag_name")
    List<TagCount> selectTagsAndCount();
}