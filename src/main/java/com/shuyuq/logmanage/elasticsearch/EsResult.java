package com.shuyuq.logmanage.elasticsearch;

import java.util.List;

/**
 * 解析NDCSKY查询ES返回结果
 *
 * @param <T> parameter
 * @version 1.1.4
 */
public class EsResult<T> {

    /**
     * 根据条件查询到的总条数
     */
    private Long total;

    /**
     * 根据查询结果返
     */
    private List<T> messages;

    /**
     * Gets total *
     *
     * @return the total
     */
    public Long getTotal() {
        return total;
    }

    /**
     * Sets total *
     *
     * @param total total
     * @return the total
     */
    public EsResult setTotal(Long total) {
        this.total = total;
        return this;
    }

    /**
     * Gets messages *
     *
     * @return the messages
     */
    public List<T> getMessages() {
        return messages;
    }

    /**
     * Sets messages *
     *
     * @param messages messages
     * @return the messages
     */
    public EsResult setMessages(List<T> messages) {
        this.messages = messages;
        return this;
    }
}
