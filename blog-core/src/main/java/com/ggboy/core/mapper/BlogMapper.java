package com.ggboy.core.mapper;

import com.ggboy.core.domain.DO.BlogInfoDO;
import com.ggboy.core.domain.DO.BlogListDO;
import com.ggboy.core.domain.query.BlogListQuery;
import com.ggboy.core.domain.query.BlogShowQuery;
import com.ggboy.core.domain.query.BlogUpdateDbreq;
import com.ggboy.core.domain.query.BlogUpdateQuery;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface BlogMapper {

    @Insert("INSERT INTO blog (blog_id,head_img,title,content,status,create_time,modify_time) VALUES " +
            "(#{blogId,jdbcType=VARCHAR},#{headImg,jdbcType=VARCHAR},#{title,jdbcType=VARCHAR}," +
            "#{content,jdbcType=BLOB},#{status,jdbcType=VARCHAR},now(),now())")
    Integer insert(@Param("blogId") String blogId, @Param("headImg") String headImg, @Param("title") String title,
                   @Param("content") byte[] content, @Param("status") String status);

    @SelectProvider(type = Provider.class, method = "queryBlogList")
    List<BlogListDO> selectList(BlogListQuery query);

    @SelectProvider(type = Provider.class, method = "queryBlogByIdAndStatus")
    BlogInfoDO selectForShow(BlogShowQuery query);

    @SelectProvider(type = Provider.class, method = "queryBlogByIdAndStatus")
    BlogInfoDO selectForUpdate(BlogUpdateQuery query);

    @UpdateProvider(type = Provider.class, method = "updateBlog")
    Integer update(BlogUpdateDbreq dbreq);
}