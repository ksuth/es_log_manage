package com.shuyuq.logmanage.elasticsearch.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 日志模块
 */
@ApiModel(value = "日志模块")
public enum EsLogModelEnum {

    /**
     * 登录登出模块
     */
    @ApiModelProperty(value = "登录登出", example = "login")
    LOGIN("login", "登录登出");

    /**
     * 编码
     */
    private String code;

    /**
     * 说明
     */
    private String message;

    /**
     * 构造器
     * @param code 编码
     * @param message 说明
     */
    EsLogModelEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * Gets code *
     *
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets code *
     *
     * @param code code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Gets message *
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets message *
     *
     * @param message message
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
