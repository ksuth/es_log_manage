package com.shuyuq.logmanage.elasticsearch;



import com.shuyuq.logmanage.elasticsearch.dto.EsLog;
import com.shuyuq.logmanage.elasticsearch.dto.EsLogModelEnum;
import com.shuyuq.logmanage.elasticsearch.dto.EsLogTypeEnum;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 初始化日志记录信息工具
 */
public class EsLogUtil {


    /**
     * 初始化登录登出模块日志详情
     * @param userName 登录用户名
     * @param mark 前后台标志 0:前台 1:后台
     * @return 日志内容模板
     */
    public static EsLog loginModel(String userName, String mark) {
        EsLog esLog = new EsLog();
        esLog.setUpdateDate(new SimpleDateFormat("YYYY-MM-dd HH:mm").format(new Date()));
        esLog.setModel(EsLogModelEnum.LOGIN.getCode());
        esLog.setUserName(userName);
        esLog.setType(EsLogTypeEnum.SYSTEM.getCode());
        esLog.setMark(mark);
        return esLog;
    }
}
