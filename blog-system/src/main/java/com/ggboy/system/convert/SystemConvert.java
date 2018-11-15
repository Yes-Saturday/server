package com.ggboy.system.convert;

import com.ggboy.system.domain.VO.FriendLinkVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SystemConvert {
    public final static FriendLinkVO convertToFriendLinkVO(Map<String, Object> map) {
        if (map == null)
            return null;
        var vo = new FriendLinkVO();

        vo.setName((String) map.get("name"));
        vo.setUrl((String) map.get("url"));

        return vo;
    }

    public final static List<FriendLinkVO> convertToFriendLinkVOs(List<Map<String, Object>> mapList) {
        if (mapList == null)
            return null;

        var vos = new ArrayList<FriendLinkVO>(mapList.size());
        for (var item : mapList)
            vos.add(convertToFriendLinkVO(item));

        return vos;
    }
}
