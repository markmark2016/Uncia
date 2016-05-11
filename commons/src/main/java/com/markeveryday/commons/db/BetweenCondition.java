package com.markeveryday.commons.db;

/**
 * 代表在两个值之间的条件
 *
 * @author liming
 */
public class BetweenCondition extends CompareCondition {
    /**
     * 创建一个Between条件
     *
     * @param propertyName 属性名
     * @param minValue     最小值
     * @param maxValue     最大值
     */
    public BetweenCondition(String propertyName, Object minValue, Object maxValue) {
        super(propertyName, minValue, maxValue);
    }
}
