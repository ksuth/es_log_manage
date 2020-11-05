package com.shuyuq.logmanage.elasticsearch;


import com.shuyuq.logmanage.elasticsearch.dto.EsLog;
import com.shuyuq.logmanage.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;
import org.elasticsearch.ElasticsearchStatusException;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.client.indices.PutIndexTemplateRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * NDCSKY ES操作封装
 *
 * @version 1.1.4
 */
@Component
@Slf4j
public class EsComponent {

    /**
     * esindex前缀
     */
    public static final String PRE_ES = "shuyuq-test-";

    @Autowired(required = false)
    private RestHighLevelClient client;


    /**
     * 根据查询条件查询ES日志记录
     *
     * @param condition 查询条件
     * @return 查询结果
     * @throws IOException 底层IO异常
     */
    public SearchResponse searchEsLogByCondition(EsCondition condition) throws IOException {
        BoolQueryBuilder queryBuilder = new BoolQueryBuilder();
        Map<String, String> map = condition.getCondition();
        Set<Map.Entry<String, String>> entries = map.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            String key = entry.getKey();
            String value = entry.getValue();
            queryBuilder.must(QueryBuilders.queryStringQuery(key + ":" + value));
        }
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(queryBuilder);
        sourceBuilder.from(condition.getBeginIndex());
        sourceBuilder.size(condition.getSize());
        sourceBuilder.timeout(new TimeValue(30, TimeUnit.SECONDS));
        String indexEs = "";
        /*获取查询日志的index*/
        if (null == condition.getDate()) {
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            indexEs = PRE_ES + format.format(new Date());
        } else {
            indexEs = PRE_ES + condition.getDate();
        }
        SearchRequest searchRequest = new SearchRequest(indexEs);
        searchRequest.source(sourceBuilder);
        return client.search(searchRequest, RequestOptions.DEFAULT);
    }

    /**
     * 解析ES返回结果为业务结果
     *
     * @param searchResponse ES查询结果
     * @param tClass         转义的范型
     * @param <T>            记录日志时候使用的业务常量
     * @return 业务结果
     */
    public <T> EsResult resolveSearchResponse(SearchResponse searchResponse, Class<T> tClass) {
        SearchHits hits = searchResponse.getHits();
        EsResult<T> result = new EsResult();
        result.setTotal(hits.totalHits);
        SearchHit[] searchHits = hits.getHits();
        List<T> messages = result.getMessages();
        for (SearchHit searchHit : searchHits) {
            String sourceAsString = searchHit.getSourceAsString();
            T message = JsonUtil.getClassBean(sourceAsString, tClass);
            messages.add(message);
        }
        return result;
    }

    /**
     * 创建ES的index模板
     *
     * @param tempName 模板名称 示例:cdpsap-ndcsky-test
     * @throws IOException IO异常
     */
    public void createNdcskyEsTemplete(String tempName) throws IOException {
        String esTemp = readEsTemp();
        PutIndexTemplateRequest request = new PutIndexTemplateRequest(tempName);
        request.source(esTemp, XContentType.JSON);
        client.indices().putTemplate(request, RequestOptions.DEFAULT);
    }

    /**
     * 生成ES的具体index
     *
     * @throws IOException IO异常
     */
    public void createEsIndex() throws IOException {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String indexEs = PRE_ES + format.format(new Date());
        CreateIndexRequest request = new CreateIndexRequest(indexEs);
        request.settings(Settings.builder()
                .put("index.number_of_shards", 3)
                .put("index.number_of_replicas", 2)
        );
        Map<String, Object> message = new HashMap<>();
        message.put("type", "text");
        Map<String, Object> properties = new HashMap<>();
        properties.put("message", message);
        Map<String, Object> mapping = new HashMap<>();
        mapping.put("properties", properties);
        request.mapping(mapping);
        client.indices().create(request, RequestOptions.DEFAULT);
    }

    /**
     * 获取index类型
     *
     * @param index index
     * @return 类型
     * @throws IOException IO异常
     */
    public String getIndexType(String index) throws IOException {
        GetIndexRequest request = new GetIndexRequest(index);
        request.includeDefaults(true);
        try {
            GetIndexResponse getIndexResponse = client.indices().get(request, RequestOptions.DEFAULT);
            return getIndexResponse.getMappings().get(index).type();
        } catch (ElasticsearchStatusException e) {
            return null;
        }
    }

    /**
     * 读取es配置模板
     *
     * @return estemp
     */
    private String readEsTemp() throws IOException {
        String jsonStr = "";

        URL url = Thread.currentThread().getContextClassLoader().getResource("estemplete.json");
        String path = URLDecoder.decode(url.getPath(), "utf-8");
        File jsonFile = new File(path);
        FileReader fileReader = new FileReader(jsonFile);
        Reader reader = new InputStreamReader(new FileInputStream(jsonFile), "utf-8");
        int ch = 0;
        StringBuffer sb = new StringBuffer();
        while ((ch = reader.read()) != -1) {
            sb.append((char) ch);
        }
        fileReader.close();
        reader.close();
        jsonStr = sb.toString();
        return jsonStr;
    }

    /**
     * 日志记录器
     *
     * @param message 记录的消息内容
     */
    public void log(EsLog message) {
        log.info("【业务日志】:" + message.getMessage());
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String indexEs = PRE_ES + format.format(new Date());
        String indexType = "_doc";
        IndexRequest request = new IndexRequest(
                indexEs,
                indexType,
                UUID.randomUUID().toString());
        JSONObject json = JSONObject.fromObject(message);
        String jsonString = json.toString();
        request.source(jsonString, XContentType.JSON);
        if (client != null) {
            client.indexAsync(request, RequestOptions.DEFAULT, new ActionListener<IndexResponse>() {
                @Override
                public void onResponse(IndexResponse indexResponse) {
                    log.info("业务日志推送成功...");
                }

                @Override
                public void onFailure(Exception e) {
                    log.error("【业务日志推送失败....】" + e.getMessage());
                }
            });
        } else {
            log.info("当前未注入Es客户端....");
        }
    }

    /**
     * 转换查询条件
     *
     * @param message 查询条件对象
     * @return 查询条件的json
     */
    public String queryConditionToJson(EsLog message) {
        JsonConfig jsonConfig = new JsonConfig();
        PropertyFilter filter = new PropertyFilter() {
            public boolean apply(Object object, String fieldName, Object fieldValue) {
                if (fieldValue instanceof List) {
                    @SuppressWarnings("unchecked")
                    List<Object> list = (List<Object>) fieldValue;
                    if (list.size() == 0) {
                        return true;
                    }
                }
                //过滤条件：值为null时过滤
                return null == fieldValue;
            }
        };
        jsonConfig.setJsonPropertyFilter(filter);
        return JSONObject.fromObject(message, jsonConfig).toString();
    }


}
