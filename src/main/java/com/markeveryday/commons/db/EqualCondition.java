package com.markeveryday.commons.db;

/**
 * 相等条件
 *
 * @author liming
 */
public class EqualCondition extends CompareCondition {

    /**
     * 创建一个相等条件
     *
     * @param propertyName
     * @param value
     */
    public EqualCondition(String propertyName, Object value) {
        super(propertyName, value);
    }

}
