package com.markeveryday.commons.db;

/**
 * 排序条件
 *
 * @author liming
 */
public class OrderCondition implements java.io.Serializable {

    private static final long serialVersionUID = 131982036485132446L;
    private String propertyName;
    private boolean ascOrder;

    public OrderCondition() {
    }

    /**
     * 只有子类或本包可以使用,子类需说明orderBy顺序
     *
     * @param property 属性
     */
    protected OrderCondition(String property) {
        this.propertyName = property;
    }

    /**
     * 获取属性名
     *
     * @return 属性名
     */
    public String getPropertyName() {
        return propertyName;
    }

    /**
     * 设置属性名
     *
     * @param propertyName 待设置的属性名
     */
    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    /**
     * 是否升序排序
     *
     * @return true或者false
     */
    public boolean isAscOrder() {
        return ascOrder;
    }

    /**
     * 设置是否升序排列
     *
     * @param ascOrder true 升序, false降序
     */
    public void setAscOrder(boolean ascOrder) {
        this.ascOrder = ascOrder;
    }

}
