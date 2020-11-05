package com.shuyuq.logmanage.elasticsearch.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Es log user type enum
 */
@ApiModel(value = "用户类型")
public enum EsLogUserTypeEnum {

    /**
     * Front es log user type enum
     */
    @ApiModelProperty(value = "前台用户", example = "0")
    FRONT("0", "前台用户"),
    /**
     * Manage es log user type enum
     */
    @ApiModelProperty(value = "后台用户", example = "1")
    MANAGE("1", "后台用户");

    /**
     * 编码
     */
    private String code;

    /**
     * 说明
     */
    private String message;

    EsLogUserTypeEnum(String code, String message) {
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
     * @return the code
     */
    public EsLogUserTypeEnum setCode(String code) {
        this.code = code;
        return this;
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
     * @return the message
     */
    public EsLogUserTypeEnum setMessage(String message) {
        this.message = message;
        return this;
    }
}
