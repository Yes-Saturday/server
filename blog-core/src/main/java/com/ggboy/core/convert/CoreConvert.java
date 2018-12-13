package com.ggboy.core.convert;

import com.ggboy.core.domain.info.BlogInfo;
import com.ggboy.core.domain.info.CategoryInfo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CoreConvert {
    public final static BlogInfo convertToBlogInfo(Map<String, Object> map) {
        if (map == null)
            return null;

        var info = new BlogInfo();
        info.setBlogId((String) map.get("blog_id"));
        info.setTitle((String) map.get("title"));
        info.setSynopsis((String) map.get("synopsis"));
        info.setSynopsisImg((byte[]) map.get("synopsis_img"));
        info.setContent((byte[]) map.get("content"));
        info.setViewCount((Integer) map.get("view_count"));
        info.setFavoriteCount((Integer) map.get("favorite_count"));
        info.setTime((String) map.get("time"));
        info.setStatus((String) map.get("status"));
        info.setWeight((BigDecimal) map.get("weight"));

        return info;
    }

    public final static List<BlogInfo> convertToBlogInfos(List<Map<String, Object>> mapList) {
        if (mapList == null)
            return null;

        var infos = new ArrayList<BlogInfo>(mapList.size());
        for (Map<String, Object> item : mapList)
            infos.add(convertToBlogInfo(item));

        return infos;
    }

    public final static CategoryInfo convertToCategoryInfo(Map<String, Object> map) {
        if (map == null)
            return null;

        var info = new CategoryInfo();
        info.setCategoryId((String) map.get("category_id"));
        info.setParentId((String) map.get("parent_id"));
        info.setName((String) map.get("name"));
        info.setMemo((String) map.get("memo"));
        info.setStatus((String) map.get("status"));

        return info;
    }

    public final static List<CategoryInfo> convertToCategoryInfos(List<Map<String, Object>> mapList) {
        if (mapList == null)
            return null;

        var infos = new ArrayList<CategoryInfo>(mapList.size());
        for (Map<String, Object> item : mapList)
            infos.add(convertToCategoryInfo(item));

        return infos;
    }
}
