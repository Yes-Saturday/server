package com.ggboy.core.mapper;

import com.ggboy.core.domain.DO.TagDO;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface TagMapper {
    @InsertProvider(type = Provider.class, method = "insertTags")
    int insertTags(String id, String[] tags);

    @Delete("DELETE FROM tag WHERE id = #{id,jdbcType=VARCHAR}")
    int deleteTags(String id);

    @Select("select tag_name as tag,count(0) as count from tag group by tag_name")
    List<TagDO> selectTagsAndCount();
}