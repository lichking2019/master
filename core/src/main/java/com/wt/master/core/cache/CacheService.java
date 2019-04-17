package com.wt.master.core.cache;

/**
 * 缓存接口，定义通用的服务
 *
 * @author lichking2019@aliyun.com
 * @date Apr 16, 2019 at 6:24:19 PM
 */
public interface CacheService {

    Object get(String cacheName,Object key);

    void put(String cacheName,Object key,Object value);
}
