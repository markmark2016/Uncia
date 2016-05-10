package com.markeveryday.uncia.commons.db;

/**
 * 或关系的 {@link ConditionSet}
 *
 * @author liming
 */
public class ConditionOrSet extends ConditionSet {


    /**
     * 静态构造方法
     *
     * @param key
     * @param value
     *
     * @return
     */
    public static ConditionOrSet newInstance(String key, Object value) {
        return new ConditionOrSet(key, value);
    }

    /**
     * 静态构造方法
     */
    public static ConditionOrSet newInstance() {
        return new ConditionOrSet();
    }

    /**
     * 构造函数
     */
    public ConditionOrSet() {
        super();
    }

    /**
     * 构造函数，构建一个条件
     *
     * @param key
     * @param value
     */
    private ConditionOrSet(String key, Object value) {
        super(key, value);
    }

}
