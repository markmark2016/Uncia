package com.markeveryday.commons.db;

/**
 * 降序
 *
 * @author liming
 */
public class DescOrder extends OrderCondition {

    private static final long serialVersionUID = -2473406369581360611L;

    /**
     * 创建一个降序对象
     *
     * @param propertyName 属性名
     */
    public DescOrder(String propertyName) {
        super(propertyName);
        super.setAscOrder(false);
    }

}
