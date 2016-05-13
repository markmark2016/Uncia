package com.markeveryday.commons.db;

/**
 * 升序
 *
 * @author liming
 */
public class AscOrder extends OrderCondition {

    private static final long serialVersionUID = -2923948637027588374L;

    /**
     * 创建一个升序对象
     *
     * @param propertyName 属性名
     */
    public AscOrder(String propertyName) {
        super(propertyName);
        super.setAscOrder(true);
    }

}
