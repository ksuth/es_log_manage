package com.shuyuq.logmanage.elasticsearch;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * ndcsky es配置文件
 *
 * @version 1.1.4
 */
@Component
@Data
@ConfigurationProperties(prefix = "ndcsky.es")
public class EsProperties {
    @Value("clusterNodes")
    private String[] clusterNodes;
    @Value("username")
    private String username;
    @Value("password")
    private String password;

}
