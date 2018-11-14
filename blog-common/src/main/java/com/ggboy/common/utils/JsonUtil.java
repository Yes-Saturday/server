package com.ggboy.common.utils;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

public class JsonUtil {
    public final static Map<String, String> toMap(String json){
        Object obj = JSON.parse(json);
        return obj == null ? new HashMap<>() : (Map<String, String>) obj;
    }
}
