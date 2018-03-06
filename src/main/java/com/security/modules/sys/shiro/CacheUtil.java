package com.security.modules.sys.shiro;

/**
 * 缓存处理工具 用于指定所用的缓存管理器
 * 
 * @author lixiaoxin
 */
public class CacheUtil {
    private static CacheManager cacheManager;
    private static CacheManager lockManager;

    public static void setCacheManager(CacheManager cacheManager) {
        CacheUtil.cacheManager = cacheManager;
    }

    public static void setLockManager(CacheManager cacheManager) {
        CacheUtil.lockManager = cacheManager;
    }

    public static CacheManager getCache() {
        return cacheManager;
    }

    public static CacheManager getLockManager() {
        return lockManager;
    }

    /** 获取锁 */
    public static boolean tryLock(String key) {
        int expires = 1000 * 180;
        return lockManager.setnx(key, expires);
    }

    /** 获取锁 */
    public static boolean getLock(String key) {
        return lockManager.lock(key);
    }

    /** 解锁 */
    public static void unlock(String key) {
        lockManager.unlock(key);
    }
}