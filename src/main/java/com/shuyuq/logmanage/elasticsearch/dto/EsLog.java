package com.shuyuq.logmanage.elasticsearch.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * ES登录登出日志详情
 */
@ApiModel(value = "EsLog")
public class EsLog {

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间", example = "202020814")
    private String updateDate;
    /**
     * 用户Id
     */
    @ApiModelProperty(value = "用户Id", example = "XXZHAO")
    private String userName;
    /**
     * 所属模块
     */
    @ApiModelProperty(value = "所属模块", example = "login")
    private String model;

    /**
     * 日志类型
     */
    @ApiModelProperty(value = "日志类型,详细信息见：EsLogTypeEnum", example = "0")
    private String type;

    /**
     * 详细信息
     */
    @ApiModelProperty(value = "详细信息", example = "用户开始登录")
    private String message;

    /**
     * 前后台标志 0 ： 前台  1:后台
     */
    @ApiModelProperty(value = "前后台标志", example = "0 ： 前台  1:后台")
    private String mark;

    /**
     * Gets update date *
     *
     * @return the update date
     */
    public String getUpdateDate() {
        return updateDate;
    }

    /**
     * Sets update date *
     *
     * @param updateDate update date
     */
    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * Gets user name *
     *
     * @return the user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets user name *
     *
     * @param userName user name
     */
    public void setUserName(String userName) {
        this.userName = userName.toUpperCase();
    }

    /**
     * Gets model *
     *
     * @return the model
     */
    public String getModel() {
        return model;
    }

    /**
     * Sets model *
     *
     * @param model model
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Gets type *
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets type *
     *
     * @param type type
     */
    public void setType(String type) {
        this.type = type;
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

    /**
     * Gets mark *
     *
     * @return the mark
     */
    public String getMark() {
        return mark;
    }

    /**
     * Sets mark *
     *
     * @param mark mark
     */
    public void setMark(String mark) {
        this.mark = mark;
    }
}
