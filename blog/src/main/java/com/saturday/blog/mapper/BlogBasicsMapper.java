package com.saturday.blog.mapper;

import com.saturday.blog.domain.DO.BasicsDO;
import com.saturday.blog.domain.DO.BlogListDO;
import com.saturday.blog.domain.entry.TagCount;
import com.saturday.blog.domain.entry.BlogBasics;
import com.saturday.blog.domain.query.BaseBlogQuery;
import com.saturday.blog.domain.query.BlogListQuery;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface BlogBasicsMapper extends Mapper<BlogBasics> {
    String basics_table = "blog_basics";
    String category_table = "blog_category";
    String tag_table = "blog_tag";

    @SelectProvider(type = Provider.class, method = "queryBlogList")
    List<BlogListDO> selectBlogList(BlogListQuery query);

    @SelectProvider(type = Provider.class, method = "queryBlogByIdAndStatus")
    BasicsDO selectBlogByIdAndStatus(BaseBlogQuery query);

    @Select("SELECT blog_category.category_id as id,blog_category.name as name from " + category_table + " where blog_category.status = 'pass' and blog_category.parent_id is null")
    List<Map<String, Object>> selectParentCategory();

    @Select("select tag_name as tag,count(0) as count from " + tag_table + " group by tag_name")
    List<TagCount> selectTagsAndCount();

    @Select("select tag_name from " + tag_table + " where id = #{id,jdbcType=VARCHAR}")
    List<String> selectTagsByBlogId(String id);
}