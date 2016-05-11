package com.markeveryday.commons.db;

/**
 * 小于 条件
 *
 * @author liming
 */
public class LessThanCondition extends CompareCondition {
    /**
     * 创建一个小于的条件
     *
     * @param propertyName 属性名
     * @param value        小于的值
     */
    public LessThanCondition(String propertyName, Object value) {
        super(propertyName, value);
    }
}
