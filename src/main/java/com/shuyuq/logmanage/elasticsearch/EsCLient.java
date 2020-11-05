package com.shuyuq.logmanage.elasticsearch;


import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpHost;
import org.apache.http.ParseException;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 初始化ES客户端
 */
@Configuration
public class EsCLient {

    /**
     * es集群配置
     */
    private final EsProperties properties;

    /**
     * Ndc sky es c lient
     *
     * @param properties properties
     */
    public EsCLient(@Autowired EsProperties properties) {
        this.properties = properties;
    }


    /**
     * Elasticsearch client rest high level client
     *
     * @return the rest high level client
     */
    @Bean(value = "ndcskyEsClient")
    public RestHighLevelClient elasticsearchClient() {
        /*在没有配置的情况下不添加client*/
        if ("username".equals(properties.getUsername())) {
            return null;
        }
        RestClientBuilder restClientBuilder = RestClient.builder(
                getEsHosts()).setDefaultHeaders(
                    getBasicAuth(
                        encodeBasicAuth(
                                properties.getUsername(), properties.getPassword(), StandardCharsets.ISO_8859_1)));
        return new RestHighLevelClient(restClientBuilder);
    }


    /**
     * Get es hosts http host [ ]
     *
     * @return the http host [ ]
     */
    private HttpHost[] getEsHosts() {
        HttpHost[] hosts = new HttpHost[6];
        String[] clusterNodes = properties.getClusterNodes();
        for (int i = 0; i < clusterNodes.length; i++) {
            String[] nodeInfo = clusterNodes[i].split(":");
            HttpHost httpHost = new HttpHost(nodeInfo[0], Integer.valueOf(nodeInfo[1]), "http");
            hosts[i] = httpHost;
        }
        return hosts;
    }

    /**
     * Get basic auth header [ ]
     *
     * @param encodedCredentials encoded credentials
     * @return the header [ ]
     */
    private Header[] getBasicAuth(String encodedCredentials) {
        Assert.hasText(encodedCredentials, "'encodedCredentials' must not be null or blank");
        Header[] headers = new Header[]{
                new Header() {
                    @Override
                    public String getName() {
                        return "Authorization";
                    }

                    @Override
                    public String getValue() {
                        return "Basic " + encodedCredentials;
                    }

                    @Override
                    public HeaderElement[] getElements() throws ParseException {
                        return new HeaderElement[0];
                    }
                }
        };
        return headers;
    }

    /**
     * Encode basic auth string
     *
     * @param username username
     * @param password password
     * @param charset  charset
     * @return the string
     */
    private static String encodeBasicAuth(String username, String password, Charset charset) {
        Assert.notNull(username, "Username must not be null");
        Assert.doesNotContain(username, ":", "Username must not contain a colon");
        Assert.notNull(password, "Password must not be null");
        CharsetEncoder encoder = charset.newEncoder();
        if (encoder.canEncode(username) && encoder.canEncode(password)) {
            String credentialsString = username + ":" + password;
            byte[] encodedBytes = Base64.getEncoder().encode(credentialsString.getBytes(charset));
            return new String(encodedBytes, charset);
        } else {
            throw new IllegalArgumentException(
                    "Username or password contains characters that cannot be encoded to " + charset.displayName());
        }
    }

}
