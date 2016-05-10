package com.markeveryday.uncia.commons.db;

import java.util.HashMap;
import java.util.Map;

/**
 * 分页Bean,用于在查询时进行分页
 *
 * @author liming
 *         <p>
 *         TODO 列举例子
 */
public class Page implements java.io.Serializable {

    private static final long serialVersionUID = 693435998917420886L;

    public static final int FIRST_PAGE_NUM = 1;

    /**
     * 生成分页时所带的参数键值对
     */
    private Map<String, Object> parameterMap;
    /**
     * 当前页数
     */
    private int currentPage = FIRST_PAGE_NUM;

    /**
     * 总记录数
     */
    private int recordsCount = -1;
    /**
     * 一次查询的最大记录数
     */
    private Integer maxRecords = 15;

    /**
     * 总页面数
     */
    private int pageCount;

    public Page() {
    }

    /**
     * 创建一个Page
     *
     * @param currentPage 当前页
     * @param maxRecords  最大记录数
     */
    public Page(int currentPage, int maxRecords) {
        this.currentPage = currentPage;
        this.maxRecords = maxRecords;
    }

    /**
     * 创建一个Page
     *
     * @param maxRecords 最大记录数
     */
    public Page(int maxRecords) {
        this.maxRecords = maxRecords;
    }

    /**
     * 创建一个Page
     *
     * @param currentPage  当前页
     * @param recordsCount 记录数
     * @param params       参数
     */
    public Page(int currentPage, int recordsCount, Map<String, Object> params) {
        this.currentPage = currentPage;
        this.recordsCount = recordsCount;
        this.parameterMap = params;
    }

    /**
     * 增加参数
     *
     * @param key   参数键
     * @param value 参数值
     */
    public void putParameter(String key, Object value) {
        if (parameterMap == null) {
            parameterMap = new HashMap<String, Object>();
        }

        parameterMap.put(key, value);
    }

    /**
     * 获取当前页
     *
     * @return 当前页
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * 获取记录总数
     *
     * @return 记录总数
     */
    public int getRecordsCount() {
        return recordsCount;
    }

    /**
     * 获取单页最大记录数
     *
     * @return 单页最大记录数
     */
    public Integer getMaxRecords() {
        return maxRecords;
    }

    /**
     * 设置当前页
     *
     * @param currentPage 当前页
     */
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    /**
     * 设置总记录数
     *
     * @param recordsCount 总记录数
     */
    public void setRecordsCount(int recordsCount) {
        this.recordsCount = recordsCount;
    }

    /**
     * 设置参数Map
     *
     * @param parameterMap 参数Map
     */
    public void setParameterMap(Map<String, Object> parameterMap) {
        this.parameterMap = parameterMap;
    }

    /**
     * 设置一次查询的最大记录数
     *
     * @param maxRecords 记录数
     */
    public void setMaxRecords(Integer maxRecords) {
        this.maxRecords = maxRecords;
    }

    /**
     * 增加当前页
     */
    public void incrementCurrentPage() {
        this.currentPage++;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("currentPage:" + currentPage);
        stringBuilder.append("maxRecords:" + maxRecords);
        stringBuilder.append("recordsCount:" + recordsCount);
        stringBuilder.append("parameterMap:" + parameterMap);
        return stringBuilder.toString();
    }

    /**
     * 设置页数
     *
     * @param pageCount
     */
    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    /**
     * 获取页数
     *
     * @return
     */
    public int getPageCount() {
        if (pageCount <= 0) {
            pageCount = recordsCount / maxRecords;
            if (recordsCount % maxRecords > 0) {
                pageCount += 1;
            }
        }
        return pageCount;
    }

}

