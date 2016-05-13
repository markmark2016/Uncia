package com.markeveryday.commons.db;

/**
 * 不等于 条件
 *
 * @author liming
 */
public class NotEqualCondition extends CompareCondition {
    /**
     * 创建一个不等于条件
     *
     * @param propertyName 属性名
     * @param value        不等于的值
     */
    public NotEqualCondition(String propertyName, Object value) {
        super(propertyName, value);
    }
}
