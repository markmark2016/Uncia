package com.markeveryday.commons.cache;

import java.io.Serializable;
import java.util.List;

/**
 * 缓存接口
 *
 * @author liming
 */
interface MarkCache extends Serializable {

    /**
     * 从缓存中获取指定的值
     *
     * @param key 值的key
     *
     * @return 缓存的对象或者 <tt>null</tt>
     */
    <X> X get(Object key);

    /**
     * 向缓存中添加一个键值对
     *
     * @param key   键
     * @param value 值
     */
    void put(Object key, Object value);

    /**
     * 向缓存中静默的添加一个键值对, 不更新统计数据和监听器
     *
     * @param key   键
     * @param value 值
     */
    void putQuiet(Object key, Object value);

    /**
     * 从缓存中移除一个数据
     *
     * @param key 待移除的键
     */
    void remove(Object key);

    /**
     * 清空缓存
     */
    void clear();

    /**
     * 销毁缓存
     */
    void destroy();

    /**
     * 获取缓存的区
     *
     * @return 字符串表示的缓存区域
     */
    String getRegionName();

    /**
     * 获取当前缓存区在内存中的字节数
     *
     * @return 字节数或者 -1
     */
    long getSizeInMemory();

    /**
     * 获取当前缓存区缓存的条目数
     *
     * @return 条目数或者-1
     */
    long getElementCountInMemory();

    /**
     * 获取缓存到硬盘上的条目数
     *
     * @return 硬盘上的条目数量或01
     */
    long getElementCountOnDisk();

    /**
     * 获取所有的已缓存的条目
     *
     * @return 所有的已缓存的条目
     */
    @SuppressWarnings("unchecked")
    List getKeys();
}

