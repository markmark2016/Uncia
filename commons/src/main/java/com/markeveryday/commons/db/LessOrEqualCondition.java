package com.markeveryday.commons.db;

/**
 * 小于或等于条件
 *
 * @author liming
 */
public class LessOrEqualCondition extends CompareCondition {
    /**
     * 创建一个小于或者等于条件
     *
     * @param propertyName 属性名
     * @param value        小于或者等于的值
     */
    public LessOrEqualCondition(String propertyName, Object value) {
        super(propertyName, value);
    }
}
