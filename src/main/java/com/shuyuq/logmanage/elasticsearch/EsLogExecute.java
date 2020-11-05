package com.shuyuq.logmanage.elasticsearch;

import com.shuyuq.logmanage.elasticsearch.dto.EsLog;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * ES日志记录器
 *
 * @author shuyuq
 * @version 1.1.5
 */
@Component
@Slf4j
public class EsLogExecute {


    @Autowired
    private EsComponent util;

    /**
     * 登录登出日志记录器
     *
     * @param userName 登录用户名
     * @param mark     前后台标志 0:前台 1:后台
     * @param message  消息内容
     */
    public void login(String userName, String mark, String message) {
        EsLog esLog = EsLogUtil.loginModel(userName, mark);
        esLog.setMessage(message);
        util.log(esLog);
    }


    /**
     * 根据添加查询ES返回结果
     *
     * @param condition condition
     * @return SearchResponse
     * @throws IOException IO异常
     */
    public SearchResponse searchLogByCondition(EsCondition condition) throws IOException {
        return util.searchEsLogByCondition(condition);
    }


}
