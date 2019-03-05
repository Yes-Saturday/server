package com.saturday.common.utils;

import com.saturday.common.exception.InternalException;

import java.io.IOException;
import java.util.HashMap;
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

    /**
     * only putCopy can getCopy
     */
    public final static <T> T getCopy(String key) {
        try {
            return (T) IoUtil.byte2Obj(get(key));
        } catch (IOException | ClassNotFoundException e) {
            throw new InternalException("TODO", "反序列化失败");
        }
    }

    public final static void putCopy(String key, Object data, int expTime) {
        try {
            put(key, IoUtil.obj2Byte(data), expTime);
        } catch (IOException e) {
            throw new InternalException("TODO", "序列化失败");
        }
    }

    public final static void copyToNewKey(String key, String newKey, int expTime) {
        var data = get(key);
        if (data == null)
            return;
        put(newKey, data, expTime);
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

    public final static Map<String, Object> list() {
        var result = new HashMap<String, Object>(cacheMap.size());
        var iterator = cacheMap.entrySet().iterator();
        while (iterator.hasNext()) {
            var item = iterator.next();
            result.put(item.getKey(), new HashMap<String, Object>(2) {{
                put("data", item.getValue().getData());
                put("countdown", item.getValue().countdown() / 1000 + "s");
            }});
        }
        return result;
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

    long countdown() {
        return invalidTime - System.currentTimeMillis();
    }

    Object getData() {
        return data;
    }
}