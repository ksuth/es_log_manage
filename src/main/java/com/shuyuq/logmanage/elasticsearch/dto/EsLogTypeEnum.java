package com.shuyuq.logmanage.elasticsearch.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 日志类型（增删改查）
 */
@ApiModel(value = "日志类型")
public enum EsLogTypeEnum {

    /**
     * SYSTEM log type
     */
    @ApiModelProperty(value = "系统信息", example = "0")
    SYSTEM("0", "系统信息"),
    /**
     * Create log type
     */
    @ApiModelProperty(value = "新增", example = "1")
    CREATE("1", "新增"),
    /**
     * Update log type
     */
    @ApiModelProperty(value = "修改", example = "2")
    UPDATE("2", "修改"),
    /**
     * Delete log type
     */
    @ApiModelProperty(value = "删除", example = "3")
    DELETE("3", "删除");

    /**
     * 编码
     */
    private String code;

    /**
     * 说明
     */
    private String message;

    EsLogTypeEnum(String code, String message) {
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
