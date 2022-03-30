package edu.wj.sport.service.memory;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 运行时缓存信息
 * 保存用户临时信息
 */
public class MemoryCache {

    private static final ConcurrentHashMap<String, String> userCache = new ConcurrentHashMap<>();

    private MemoryCache(){}


    /**
     * 添加用户登录缓存信息
     * @param userId
     * @param deviceInfo
     */
    public static void putCache(String userId, String deviceInfo){
        userCache.remove(userId);
        userCache.put(userId, deviceInfo);
    }


    /**
     * 校验用户登录缓存信息
     * @param userId
     * @param deviceInfo
     * @return
     */
    public static boolean check(String userId, String deviceInfo){
        String cacheInfo = userCache.get(userId);
        if (cacheInfo == null || cacheInfo.isEmpty()){
            return false;
        }
        return cacheInfo.equals(deviceInfo);
    }

}
