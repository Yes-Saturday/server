package com.ggboy.core.convert;

import com.ggboy.core.domain.VO.BlogVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BlogConvert {
    public final static BlogVO convertToBlogVO(Map<String, Object> map) {
        if (map == null)
            return null;

        BlogVO blogVO = new BlogVO();
        blogVO.setBlogId((String) map.get("BlogId"));
        blogVO.setTitle((String) map.get("Title"));
        blogVO.setSynopsis((String) map.get("Synopsis"));
        blogVO.setContent((String) map.get("Content"));
        blogVO.setViewCount((String) map.get("ViewCount"));
        blogVO.setFavoriteCount((String) map.get("FavoriteCount"));
        blogVO.setModifyTime((String) map.get("modifyTime"));

        return blogVO;
    }

    public final static List<BlogVO> convertToBlogVOs(List<Map<String, Object>> mapList) {
        if (mapList == null)
            return null;

        var blogVOs = new ArrayList<BlogVO>(mapList.size());
        for (Map<String, Object> item : mapList)
            blogVOs.add(convertToBlogVO(item));

        return blogVOs;
    }
}
