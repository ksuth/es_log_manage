/*
 * Copyright(c): 2018 中国民航信息网络股份有限公司 All rights reserved.
 * 密级：CONFIDENTIAL
 * 项目名称：新一代航空公司业务前端
 * 模块名称：cdp
 * 文件名称：IRegisteredService
 * 创建日期：2019-10-18
 * 注意：本内容仅限于中国民航信息网络股份有限公司内部传阅，禁止外泄以及用于其他的商业目的.
 */
package com.shuyuq.logmanage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Description: springboot启动类
 *
 * @author GUOFEI 2019-09-04 10:45
 * @version v1.0
 * @since v1.0
 */
@SpringBootApplication(scanBasePackages = "com")
@ComponentScan(basePackages = {"com"})
/** 开启事务 */
@EnableTransactionManagement
public class WebApplication extends SpringBootServletInitializer {
    /**
     * springboot 入口
     *
     * @param application application
     * @return SpringApplicationBuilder
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WebApplication.class);
    }

    /**
     * @version 1.0
     *     Description:键盘音效
     *
     *     ......................我佛慈悲......................
     *                       _oo0oo_
     *                      o8888888o
     *                      88" . "88
     *                      (| -_- |)
     *                      0\  =  /0
     *                    ___/`---'\___
     *                  .' \\|     |// '.
     *                 / \\|||  :  |||// \
     *                / _||||| -卍-|||||- \
     *               |   | \\\  -  /// |   |
     *               | \_|  ''\---/''  |_/ |
     *               \  .-\__  '-'  ___/-. /
     *             ___'. .'  /--.--\  `. .'___
     *          ."" '<  `.___\_<|>_/___.' >' "".
     *         | | :  `- \`.;`\ _ /`;.`/ - ` : | |
     *         \  \ `_.   \_ __\ /__ _/   .-` /  /
     *     =====`-.____`.___ \_____/___.-`___.-'=====
     *                       `=---='
     *
     *..................佛祖开光 ,永无BUG...................
     *
     */
    /**
     * @param args args
     */
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

}
