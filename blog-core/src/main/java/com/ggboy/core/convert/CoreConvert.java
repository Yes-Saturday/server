package com.ggboy.core.convert;

import com.ggboy.core.domain.VO.BlogVO;
import com.ggboy.core.domain.VO.CategoryVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CoreConvert {
    public final static BlogVO convertToBlogVO(Map<String, Object> map) {
        if (map == null)
            return null;

        var vo = new BlogVO();
        vo.setBlogId((String) map.get("blog_id"));
        vo.setTitle((String) map.get("title"));
        vo.setSynopsis((String) map.get("synopsis"));
        vo.setContent((String) map.get("content"));
        vo.setViewCount((Integer) map.get("view_count"));
        vo.setFavoriteCount((Integer) map.get("favorite_count"));
        vo.setTime((String) map.get("time"));
        vo.setMemo((String) map.get("memo"));

        return vo;
    }

    public final static List<BlogVO> convertToBlogVOs(List<Map<String, Object>> mapList) {
        if (mapList == null)
            return null;

        var blogVOs = new ArrayList<BlogVO>(mapList.size());
        for (Map<String, Object> item : mapList)
            blogVOs.add(convertToBlogVO(item));

        return blogVOs;
    }

    public final static CategoryVO convertToCategoryVO(Map<String, Object> map) {
        if (map == null)
            return null;

        var vo = new CategoryVO();
        vo.setId((String) map.get("category_id"));
        vo.setName((String) map.get("name"));
        return vo;
    }

    public final static List<CategoryVO> convertToCategoryVOs(List<Map<String, Object>> mapList) {
        if (mapList == null)
            return null;

        var vos = new ArrayList<CategoryVO>(mapList.size());
        for (Map<String, Object> item : mapList)
            vos.add(convertToCategoryVO(item));

        return vos;
    }
}
