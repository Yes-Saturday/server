package com.ggboy.core.convert;

import com.ggboy.common.utils.StringUtil;
import com.ggboy.core.domain.VO.BlogVO;
import com.ggboy.core.domain.VO.CategoryVO;
import com.ggboy.core.domain.request.UpdateBlogRequest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
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
        vo.setStatus((String) map.get("status"));
        vo.setWeight((BigDecimal) map.get("weight"));

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

    public final static Map<String, Object> convertToUpdateDO(UpdateBlogRequest req) {
        if (req == null)
            return null;

        var doMap = new HashMap<String, Object>();
        doMap.put("blog_id", req.getId());
        doMap.put("title", req.getTitle());
        doMap.put("synopsis", req.getSynopsis());
        doMap.put("content", StringUtil.toBytes(req.getContent()));
        doMap.put("status", req.getStatus());
        doMap.put("weight", req.getWeight());

        return doMap;
    }
}
