/*
 * Copyright(c): 2018 中国民航信息网络股份有限公司 All rights reserved.
 * 密级：CONFIDENTIAL
 * 项目名称：新一代航空公司业务前端
 * 模块名称：cdp
 * 文件名称：ITokenServiceImpl
 * 创建日期：2019-10-17
 * 注意：本内容仅限于中国民航信息网络股份有限公司内部传阅，禁止外泄以及用于其他的商业目的.
 */
package com.shuyuq.logmanage.util;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.io.StringWriter;

/**
 * Description: JSon工具类
 *
 * @author GUOFEI 2019-09-19 16:51
 * @version v1.0.0
 * @since v1.0.0
 */
public final class JsonUtil {
    /**
     * 构造器
     */
    private JsonUtil() {
    }

    /**
     * json转实体bean
     *
     * @param object object
     * @param t      class
     * @param <T>    实体
     * @return 实体
     */
    public static <T> T getClassBean(Object object, Class<T> t) {
        JSONObject jsonObject = JSONObject.fromObject(object);
        return (T) JSONObject.toBean(jsonObject, t);
    }

    /**
     * bean转json
     *
     * @param obj obj
     * @return string
     * @throws IOException 异常
     */
    public static String bean2Json(Object obj) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        StringWriter sw = new StringWriter();
        JsonGenerator gen = new JsonFactory().createJsonGenerator(sw);
        mapper.writeValue(gen, obj);
        gen.close();
        return sw.toString();
    }
}
