package com.markeveryday.uncia.commons.db;

/**
 * 大于 条件
 *
 * @author liming
 */
public class GreaterCondition extends CompareCondition {
    /**
     * 创建一个大于的条件
     *
     * @param propertyName 属性名
     * @param value        值,大于某值
     */
    public GreaterCondition(String propertyName, Object value) {
        super(propertyName, value);
    }
}
