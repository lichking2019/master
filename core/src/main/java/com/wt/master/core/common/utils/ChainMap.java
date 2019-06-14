package com.wt.master.core.common.utils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 基于链式的Map
 *
 * @author lichking2019@aliyun.com
 * @date May 28, 2019 at 4:10:36 PM
 */
public class ChainMap<K, V> extends LinkedHashMap<K, V> {

    /**
     * 添加一条记录
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public ChainMap<K, V> chainPut(K key, V value) {
        super.put(key, value);
        return this;
    }

    /**
     * 批量添加
     *
     * @param map 键值集合
     * @return
     */
    public ChainMap<K, V> chainPutAll(Map map) {
        super.putAll(map);
        return this;
    }


}
