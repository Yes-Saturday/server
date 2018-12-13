package com.ggboy.system.convert;

import com.ggboy.system.domain.info.PublisherInfo;

import java.util.Map;

public class SystemConvert {
    public final static PublisherInfo convertToPublisherInfo(Map<String, Object> map) {
        if (map == null)
            return null;

        PublisherInfo info = new PublisherInfo();
        info.setId((String) map.get("id"));
        info.setName((String) map.get("name"));
        info.setStatus((String) map.get("status"));

        return info;
    }
}
