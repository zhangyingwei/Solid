package com.github.zhangyingwei.solid.cache;

import java.util.concurrent.ConcurrentHashMap;

public class CacheBuilder {
    private static ConcurrentHashMap<String, SolidCache> cacheMap = new ConcurrentHashMap<String,SolidCache>();

    public static SolidCache getOrCreateCache(String key) {
        if (!cacheMap.contains(key)) {
            cacheMap.put(key, new SolidCache());
        }
        return cacheMap.get(key);
    }
}
