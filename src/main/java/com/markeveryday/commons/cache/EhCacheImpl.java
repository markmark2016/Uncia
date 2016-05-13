package com.markeveryday.commons.cache;

import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * MarkCache缓存的实现, 通过ehcache
 *
 * @author liming
 */
public class EhCacheImpl implements MarkCache {

    private Cache cache;

    public EhCacheImpl(Cache cache) {
        this.cache = cache;
    }

    /**
     * 从缓存中获取指定的值
     *
     * @param key 值的key
     *
     * @return 缓存的对象或者 <tt>null</tt>
     */
    @Override
    public <X> X get(Object key) {
        if (key == null) {
            return null;
        } else {
            Element element = cache.get(key);

            if (element == null) {
                return null;
            } else {
                return (X) element.getValue();
            }
        }
    }

    /**
     * 向缓存中添加一个键值对
     *
     * @param key   键
     * @param value 值
     */
    @Override
    public void put(Object key, Object value) {
        Element element = new Element(key, value);
        cache.put(element);
    }

    /**
     * 向缓存中静默的添加一个键值对, 不更新统计数据和监听器
     *
     * @param key   键
     * @param value 值
     */
    @Override
    public void putQuiet(Object key, Object value) {
        Element element = new Element(key, value);
        cache.putQuiet(element);
    }

    /**
     * 从缓存中移除一个数据
     *
     * @param key 待移除的键
     */
    @Override
    public void remove(Object key) {
        cache.remove(key);
    }

    /**
     * 清空缓存
     */
    @Override
    public void clear() {
        cache.removeAll();
    }

    /**
     * 销毁缓存
     */
    @Override
    public void destroy() {
        CacheManager cm = CacheManager.getInstance();
        if (null != cm) {
            cm.removeCache(cache.getName());
        }
    }

    /**
     * 获取缓存的区
     *
     * @return 字符串表示的缓存区域
     */
    @Override
    public String getRegionName() {
        return cache.getName();
    }

    /**
     * 获取当前缓存区在内存中的字节数
     *
     * @return 字节数或者 -1
     */
    @Override
    public long getSizeInMemory() {
        try {
            return cache.calculateInMemorySize();
        } catch (Throwable t) {
            return -1;
        }
    }

    /**
     * 获取当前缓存区缓存的条目数
     *
     * @return 条目数或者-1
     */
    @Override
    public long getElementCountInMemory() {
        try {
            return cache.getSize();
        } catch (CacheException ex) {
            return 0;
        }
    }

    /**
     * 获取缓存到硬盘上的条目数
     *
     * @return 硬盘上的条目数量或01
     */
    @Override
    public long getElementCountOnDisk() {
        return cache.getDiskStoreSize();
    }

    /**
     * 获取所有的已缓存的条目
     *
     * @return 所有的已缓存的条目
     */
    @Override
    public List getKeys() {
        try {
            return cache.getKeys();
        } catch (IllegalStateException ex) {
            return null;
        } catch (CacheException ex) {
            return null;
        }
    }
}
