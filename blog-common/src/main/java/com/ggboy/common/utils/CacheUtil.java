package com.ggboy.common.utils;

import com.ggboy.common.exception.InternalException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CacheUtil {
    private final static Map<String, Cache> cacheMap = new ConcurrentHashMap<>();

    public final static <T> T get(String key) {
        Cache cache = cacheMap.get(key);
        return cache == null || cache.isInvalid() ? null : (T) cache.getData();
    }

    public final static void put(String key, Object data, int expTime) {
        gc(false);
        if (cacheMap.size() > 1 << 10)
            gc(true);
        if (cacheMap.size() > 1 << 10)
            throw new InternalException("STATIC_MAP_TO_MANY_ENTRY", "静态Map内容过多");
        cacheMap.put(key, new Cache(data, expTime));
    }

    private final static void gc(boolean now) {
        var gcTime = cacheMap.get("gc#>time");

        if (gcTime == null)
            cacheMap.put("gc#>time", gcTime = new Cache(null, 60 * 10));

        if (!gcTime.isInvalid() && !now)
            return;

        gcTime.setInvalidTime(60 * 10);
        var iterator = cacheMap.entrySet().iterator();
        while (iterator.hasNext()) {
            var entry = iterator.next();
            if (entry.getValue().isInvalid())
                iterator.remove();
        }
    }

    public final static void clear() {
        var gcTime = cacheMap.get("gc#>time");

        cacheMap.clear();

        if (gcTime != null) {
            gcTime.setInvalidTime(60 * 10);
            cacheMap.put("gc#>time", gcTime);
        }
    }
}

class Cache {
    private long invalidTime;
    private Object data;

    Cache(Object data, int expTime) {
        this.data = data;
        this.invalidTime = System.currentTimeMillis() + expTime * 1000;
    }

    boolean isInvalid() {
        return System.currentTimeMillis() > invalidTime;
    }

    void setInvalidTime(int expTime) {
        this.invalidTime = System.currentTimeMillis() + expTime * 1000;
    }

    Object getData() {
        return data;
    }
}