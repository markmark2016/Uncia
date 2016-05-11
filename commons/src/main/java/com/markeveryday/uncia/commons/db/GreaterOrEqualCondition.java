package com.markeveryday.uncia.commons.db;

/**
 * 大于或等于条件
 *
 * @author liming
 */
public class GreaterOrEqualCondition extends CompareCondition {
    /**
     * 创建一个大于或者等于的条件
     *
     * @param propertyName 属性名
     * @param value        大于或等于的值
     */
    public GreaterOrEqualCondition(String propertyName, Object value) {
        super(propertyName, value);
    }
}
