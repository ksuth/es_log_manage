package com.shuyuq.logmanage.elasticsearch;

import java.util.Map;

/**
 * ES查询条件封装
 *
 * @version 1.1.4
 */
public class EsCondition {

    /**
     * 日志生成时间:yyyyMMdd
     */
    private String date;

    /**
     * 查询条件
     */
    private Map<String, String> condition;

    /**
     * 查询起始条数
     */
    private Integer beginIndex;

    /**
     * 本次总共查询多少条数据
     */
    private Integer size;


    /**
     * Gets condition *
     *
     * @return the condition
     */
    public Map<String, String> getCondition() {
        return condition;
    }

    /**
     * Sets condition *
     *
     * @param condition condition
     * @return the condition
     */
    public EsCondition setCondition(Map<String, String> condition) {
        this.condition = condition;
        return this;
    }

    /**
     * Gets begin index *
     *
     * @return the begin index
     */
    public Integer getBeginIndex() {
        return beginIndex;
    }

    /**
     * Sets begin index *
     *
     * @param beginIndex begin index
     * @return the begin index
     */
    public EsCondition setBeginIndex(Integer beginIndex) {
        this.beginIndex = beginIndex;
        return this;
    }

    /**
     * Gets size *
     *
     * @return the size
     */
    public Integer getSize() {
        return size;
    }

    /**
     * Sets size *
     *
     * @param size size
     * @return the size
     */
    public EsCondition setSize(Integer size) {
        this.size = size;
        return this;
    }


    /**
     * Gets date *
     *
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets date *
     *
     * @param date date
     * @return the date
     */
    public EsCondition setDate(String date) {
        this.date = date;
        return this;
    }
}
