package com.saturday.blog.mapper;

import com.saturday.blog.domain.DO.BasicsDO;
import com.saturday.blog.domain.DO.BlogListDO;
import com.saturday.blog.domain.DO.TagDO;
import com.saturday.blog.domain.query.*;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface BlogMapper {
    String basics_table = "blog_basics";
    String category_table = "blog_category";
    String tag_table = "blog_tag";

    @Insert("INSERT INTO " + basics_table + " (blog_id,head_img,title,content,status,create_time,modify_time) VALUES " +
            "(#{blogId,jdbcType=VARCHAR},#{headImg,jdbcType=VARCHAR},#{title,jdbcType=VARCHAR}," +
            "#{content,jdbcType=BLOB},#{status,jdbcType=VARCHAR},now(),now())")
    Integer insertBasics(BlogInsertDbreq blogInsertDbreq);

    @SelectProvider(type = Provider.class, method = "queryBlogList")
    List<BlogListDO> selectBlogList(BlogListQuery query);

    @SelectProvider(type = Provider.class, method = "queryBlogByIdAndStatus")
    BasicsDO selectBlogByIdAndStatus(BaseBlogQuery query);

    @UpdateProvider(type = Provider.class, method = "updateBlog")
    Integer updateBlog(BlogUpdateDbreq dbreq);

    @Select("SELECT blog_category.category_id as id,blog_category.name as name from " + category_table + " where blog_category.status = 'pass' and blog_category.parent_id is null")
    List<Map<String, Object>> selectParentCategory();

    @InsertProvider(type = Provider.class, method = "insertTags")
    int insertTags(String id, String[] tags);

    @Delete("DELETE FROM " + tag_table + " WHERE id = #{id,jdbcType=VARCHAR}")
    int deleteTags(String id);

    @Select("select tag_name as tag,count(0) as count from " + tag_table + " group by tag_name")
    List<TagDO> selectTagsAndCount();

    @Select("select tag_name from " + tag_table + " where id = #{id,jdbcType=VARCHAR}")
    List<String> selectTagsByBlogId(String id);
}