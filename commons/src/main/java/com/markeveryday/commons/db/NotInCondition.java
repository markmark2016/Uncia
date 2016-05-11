package com.markeveryday.commons.db;

/**
 * 不包含的条件
 *
 * @author liming
 */
public class NotInCondition extends CompareCondition {
    /**
     * 创建一个不包含在内的条件
     *
     * @param propertyName 属性名
     * @param obj          不包含的范围的对象
     */
    public NotInCondition(String propertyName, Object obj) {
        super(propertyName, obj);
    }
}
