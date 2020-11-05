package com.shuyuq.logmanage.elasticsearch;



import com.shuyuq.logmanage.WebApplication;
import com.shuyuq.logmanage.elasticsearch.dto.EsLog;
import com.shuyuq.logmanage.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.MultiSearchRequest;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.client.indices.IndexTemplatesExistRequest;
import org.elasticsearch.client.indices.PutIndexTemplateRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * ES查询demo示例
 */
@ActiveProfiles("tst")
@WebAppConfiguration
@SpringBootTest(classes = WebApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class NdcSkyEsUtilTest {

    @Autowired
    private EsComponent esUtil;

    @Autowired
    private RestHighLevelClient client;
    private IndexRequest request;

    @Ignore
    @Test
    public void searchEsLogByConditionTest() throws IOException {
        EsCondition condition = new EsCondition();
        condition.setSize(10);
        condition.setBeginIndex(0);
        condition.setDate("20200923");
        Map<String, String> map = new HashMap<>();
        map.put("userName", "XXZHAO");
        condition.setCondition(map);
        SearchResponse searchResponse = esUtil.searchEsLogByCondition(condition);
        SearchHits hits = searchResponse.getHits();
        System.out.println("查询到的数据总量为:" + hits.totalHits);
        System.out.println("当前返回结果数量为:" + hits.getHits().length);
        SearchHit[] hits1 = hits.getHits();
        for (SearchHit hit : hits1) {
            Map<String, Object> sourceMap = hit.getSourceAsMap();
            String message = (String) sourceMap.get("message");
            System.out.println(message);
            System.out.println(hit.getSourceAsString());
        }
    }


    /**
     * 创建新的模板
     */
    @Ignore
    @Test
    public void createTemplete() throws IOException {
        PutIndexTemplateRequest request = new PutIndexTemplateRequest("shuyuq-test");
        request.source("{\n" +
                "    \"order\" : 0,\n" +
                "    \"index_patterns\" : [\n" +
                "      \"shuyuq-test-*\"\n" +
                "    ],\n" +
                "    \"settings\" : {\n" +
                "      \"index\" : {\n" +
                "        },\n" +
                "        \"mapper\": {\n" +
                "        \"dynamic\": \"false\"\n" +
                "        },\n" +
                "        \"refresh_interval\" : \"5s\",\n" +
                "        \"number_of_routing_shards\" : \"30\",\n" +
                "        \"number_of_shards\" : \"3\",\n" +
                "        \"query\" : {\n" +
                "          \"default_field\" : [\n" +
                "            \"source\",\n" +
                "            \"message\",\n" +
                "            \"stream\",\n" +
                "            \"prospector.type\",\n" +
                "            \"input.type\",\n" +
                "            \"read_timestamp\",\n" +
                "            \"fileset.module\",\n" +
                "            \"fileset.name\",\n" +
                "            \"event.dataset\",\n" +
                "            \"syslog.severity_label\",\n" +
                "            \"syslog.facility_label\",\n" +
                "            \"process.program\",\n" +
                "            \"event.outcome\",\n" +
                "            \"service.name\",\n" +
                "            \"log.level\",\n" +
                "            \"log.flags\",\n" +
                "            \"log.source.address\",\n" +
                "            \"log.file.path\",\n" +
                "            \"event.type\",\n" +
                "            \"http.request.method\",\n" +
                "            \"source_ecs.mac\",\n" +
                "            \"source_ecs.geo.continent_name\",\n" +
                "            \"source_ecs.geo.country_iso_code\",\n" +
                "            \"source_ecs.geo.region_name\",\n" +
                "            \"source_ecs.geo.city_name\",\n" +
                "            \"source_ecs.geo.region_iso_code\",\n" +
                "            \"destination.domain\",\n" +
                "            \"destination.mac\",\n" +
                "            \"destination.geo.continent_name\",\n" +
                "            \"destination.geo.country_iso_code\",\n" +
                "            \"destination.geo.region_name\",\n" +
                "            \"destination.geo.city_name\",\n" +
                "            \"destination.geo.region_iso_code\",\n" +
                "            \"user_agent.original\",\n" +
                "            \"user_agent.device\",\n" +
                "            \"user_agent.version\",\n" +
                "            \"user_agent.patch\",\n" +
                "            \"user_agent.name\",\n" +
                "            \"user_agent.os.name\",\n" +
                "            \"user_agent.os.full_name\",\n" +
                "            \"user_agent.os.version\",\n" +
                "            \"url.domain\",\n" +
                "            \"url.hostname\",\n" +
                "            \"url.path\",\n" +
                "            \"file.path\",\n" +
                "            \"network.protocol\",\n" +
                "            \"network.transport\",\n" +
                "            \"network.type\",\n" +
                "            \"beat.name\",\n" +
                "            \"beat.hostname\",\n" +
                "            \"beat.timezone\",\n" +
                "            \"beat.version\",\n" +
                "            \"tags\",\n" +
                "            \"error.message\",\n" +
                "            \"error.type\",\n" +
                "            \"meta.cloud.provider\",\n" +
                "            \"meta.cloud.instance_id\",\n" +
                "            \"meta.cloud.instance_name\",\n" +
                "            \"meta.cloud.machine_type\",\n" +
                "            \"meta.cloud.availability_zone\",\n" +
                "            \"meta.cloud.project_id\",\n" +
                "            \"meta.cloud.region\",\n" +
                "            \"docker.container.id\",\n" +
                "            \"docker.container.image\",\n" +
                "            \"docker.container.name\",\n" +
                "            \"host.name\",\n" +
                "            \"host.id\",\n" +
                "            \"host.architecture\",\n" +
                "            \"host.os.platform\",\n" +
                "            \"host.os.name\",\n" +
                "            \"host.os.build\",\n" +
                "            \"host.os.version\",\n" +
                "            \"host.os.family\",\n" +
                "            \"host.mac\",\n" +
                "            \"kubernetes.pod.name\",\n" +
                "            \"kubernetes.pod.uid\",\n" +
                "            \"kubernetes.namespace\",\n" +
                "            \"kubernetes.node.name\",\n" +
                "            \"kubernetes.container.name\",\n" +
                "            \"kubernetes.container.image\",\n" +
                "            \"apache2.access.remote_ip\",\n" +
                "            \"apache2.access.ssl.protocol\",\n" +
                "            \"apache2.access.ssl.cipher\",\n" +
                "            \"apache2.access.user_name\",\n" +
                "            \"apache2.access.method\",\n" +
                "            \"apache2.access.url\",\n" +
                "            \"apache2.access.http_version\",\n" +
                "            \"apache2.access.referrer\",\n" +
                "            \"apache2.access.agent\",\n" +
                "            \"apache2.access.user_agent.device\",\n" +
                "            \"apache2.access.user_agent.patch\",\n" +
                "            \"apache2.access.user_agent.name\",\n" +
                "            \"apache2.access.user_agent.os\",\n" +
                "            \"apache2.access.user_agent.os_name\",\n" +
                "            \"apache2.access.geoip.continent_name\",\n" +
                "            \"apache2.access.geoip.country_iso_code\",\n" +
                "            \"apache2.access.geoip.region_name\",\n" +
                "            \"apache2.access.geoip.city_name\",\n" +
                "            \"apache2.access.geoip.region_iso_code\",\n" +
                "            \"apache2.error.level\",\n" +
                "            \"apache2.error.client\",\n" +
                "            \"apache2.error.message\",\n" +
                "            \"apache2.error.module\",\n" +
                "            \"auditd.log.record_type\",\n" +
                "            \"auditd.log.old_auid\",\n" +
                "            \"auditd.log.new_auid\",\n" +
                "            \"auditd.log.old_ses\",\n" +
                "            \"auditd.log.new_ses\",\n" +
                "            \"auditd.log.acct\",\n" +
                "            \"auditd.log.pid\",\n" +
                "            \"auditd.log.ppid\",\n" +
                "            \"auditd.log.items\",\n" +
                "            \"auditd.log.item\",\n" +
                "            \"auditd.log.a0\",\n" +
                "            \"auditd.log.res\",\n" +
                "            \"auditd.log.geoip.continent_name\",\n" +
                "            \"auditd.log.geoip.city_name\",\n" +
                "            \"auditd.log.geoip.region_name\",\n" +
                "            \"auditd.log.geoip.country_iso_code\",\n" +
                "            \"auditd.log.geoip.region_iso_code\",\n" +
                "            \"elasticsearch.node.id\",\n" +
                "            \"elasticsearch.node.name\",\n" +
                "            \"elasticsearch.index.name\",\n" +
                "            \"elasticsearch.index.id\",\n" +
                "            \"elasticsearch.shard.id\",\n" +
                "            \"elasticsearch.audit.layer\",\n" +
                "            \"elasticsearch.audit.event_type\",\n" +
                "            \"elasticsearch.audit.origin_type\",\n" +
                "            \"elasticsearch.audit.principal\",\n" +
                "            \"elasticsearch.audit.realm\",\n" +
                "            \"elasticsearch.audit.roles\",\n" +
                "            \"elasticsearch.audit.action\",\n" +
                "            \"elasticsearch.audit.uri\",\n" +
                "            \"elasticsearch.audit.uri_params\",\n" +
                "            \"elasticsearch.audit.indices\",\n" +
                "            \"elasticsearch.audit.request_id\",\n" +
                "            \"elasticsearch.audit.request_method\",\n" +
                "            \"elasticsearch.audit.request\",\n" +
                "            \"elasticsearch.audit.request_body\",\n" +
                "            \"elasticsearch.audit.user_realm\",\n" +
                "            \"elasticsearch.gc.phase.name\",\n" +
                "            \"elasticsearch.gc.tags\",\n" +
                "            \"elasticsearch.server.component\",\n" +
                "            \"elasticsearch.slowlog.logger\",\n" +
                "            \"elasticsearch.slowlog.took\",\n" +
                "            \"elasticsearch.slowlog.types\",\n" +
                "            \"elasticsearch.slowlog.stats\",\n" +
                "            \"elasticsearch.slowlog.search_type\",\n" +
                "            \"elasticsearch.slowlog.source_query\",\n" +
                "            \"elasticsearch.slowlog.extra_source\",\n" +
                "            \"elasticsearch.slowlog.took_millis\",\n" +
                "            \"elasticsearch.slowlog.total_hits\",\n" +
                "            \"elasticsearch.slowlog.total_shards\",\n" +
                "            \"elasticsearch.slowlog.routing\",\n" +
                "            \"elasticsearch.slowlog.id\",\n" +
                "            \"elasticsearch.slowlog.type\",\n" +
                "            \"haproxy.destination.ip\",\n" +
                "            \"haproxy.process_name\",\n" +
                "            \"haproxy.client.ip\",\n" +
                "            \"haproxy.frontend_name\",\n" +
                "            \"haproxy.backend_name\",\n" +
                "            \"haproxy.server_name\",\n" +
                "            \"haproxy.bind_name\",\n" +
                "            \"haproxy.error_message\",\n" +
                "            \"haproxy.source\",\n" +
                "            \"haproxy.geoip.continent_name\",\n" +
                "            \"haproxy.geoip.country_iso_code\",\n" +
                "            \"haproxy.geoip.region_name\",\n" +
                "            \"haproxy.geoip.city_name\",\n" +
                "            \"haproxy.geoip.region_iso_code\",\n" +
                "            \"haproxy.termination_state\",\n" +
                "            \"haproxy.mode\",\n" +
                "            \"haproxy.http.response.captured_cookie\",\n" +
                "            \"haproxy.http.response.captured_headers\",\n" +
                "            \"haproxy.http.request.captured_cookie\",\n" +
                "            \"haproxy.http.request.captured_headers\",\n" +
                "            \"haproxy.http.request.raw_request_line\",\n" +
                "            \"icinga.debug.facility\",\n" +
                "            \"icinga.debug.severity\",\n" +
                "            \"icinga.debug.message\",\n" +
                "            \"icinga.main.facility\",\n" +
                "            \"icinga.main.severity\",\n" +
                "            \"icinga.main.message\",\n" +
                "            \"icinga.startup.facility\",\n" +
                "            \"icinga.startup.severity\",\n" +
                "            \"icinga.startup.message\",\n" +
                "            \"iis.access.server_ip\",\n" +
                "            \"iis.access.method\",\n" +
                "            \"iis.access.url\",\n" +
                "            \"iis.access.query_string\",\n" +
                "            \"iis.access.user_name\",\n" +
                "            \"iis.access.remote_ip\",\n" +
                "            \"iis.access.referrer\",\n" +
                "            \"iis.access.site_name\",\n" +
                "            \"iis.access.server_name\",\n" +
                "            \"iis.access.http_version\",\n" +
                "            \"iis.access.cookie\",\n" +
                "            \"iis.access.hostname\",\n" +
                "            \"iis.access.agent\",\n" +
                "            \"iis.access.user_agent.device\",\n" +
                "            \"iis.access.user_agent.patch\",\n" +
                "            \"iis.access.user_agent.name\",\n" +
                "            \"iis.access.user_agent.os\",\n" +
                "            \"iis.access.user_agent.os_name\",\n" +
                "            \"iis.access.geoip.continent_name\",\n" +
                "            \"iis.access.geoip.country_iso_code\",\n" +
                "            \"iis.access.geoip.region_name\",\n" +
                "            \"iis.access.geoip.city_name\",\n" +
                "            \"iis.access.geoip.region_iso_code\",\n" +
                "            \"iis.error.remote_ip\",\n" +
                "            \"iis.error.server_ip\",\n" +
                "            \"iis.error.http_version\",\n" +
                "            \"iis.error.method\",\n" +
                "            \"iis.error.url\",\n" +
                "            \"iis.error.reason_phrase\",\n" +
                "            \"iis.error.queue_name\",\n" +
                "            \"iis.error.geoip.continent_name\",\n" +
                "            \"iis.error.geoip.country_iso_code\",\n" +
                "            \"iis.error.geoip.region_name\",\n" +
                "            \"iis.error.geoip.city_name\",\n" +
                "            \"iis.error.geoip.region_iso_code\",\n" +
                "            \"kafka.log.timestamp\",\n" +
                "            \"kafka.log.level\",\n" +
                "            \"kafka.log.message\",\n" +
                "            \"kafka.log.component\",\n" +
                "            \"kafka.log.class\",\n" +
                "            \"kafka.log.trace.class\",\n" +
                "            \"kafka.log.trace.message\",\n" +
                "            \"kafka.log.trace.full\",\n" +
                "            \"kibana.log.tags\",\n" +
                "            \"kibana.log.state\",\n" +
                "            \"logstash.log.message\",\n" +
                "            \"logstash.log.level\",\n" +
                "            \"logstash.log.module\",\n" +
                "            \"logstash.log.thread\",\n" +
                "            \"logstash.slowlog.message\",\n" +
                "            \"logstash.slowlog.level\",\n" +
                "            \"logstash.slowlog.module\",\n" +
                "            \"logstash.slowlog.thread\",\n" +
                "            \"logstash.slowlog.event\",\n" +
                "            \"logstash.slowlog.plugin_name\",\n" +
                "            \"logstash.slowlog.plugin_type\",\n" +
                "            \"logstash.slowlog.plugin_params\",\n" +
                "            \"mongodb.log.severity\",\n" +
                "            \"mongodb.log.component\",\n" +
                "            \"mongodb.log.context\",\n" +
                "            \"mongodb.log.message\",\n" +
                "            \"mysql.error.timestamp\",\n" +
                "            \"mysql.error.level\",\n" +
                "            \"mysql.error.message\",\n" +
                "            \"mysql.slowlog.user\",\n" +
                "            \"mysql.slowlog.host\",\n" +
                "            \"mysql.slowlog.ip\",\n" +
                "            \"mysql.slowlog.query\",\n" +
                "            \"mysql.slowlog.schema\",\n" +
                "            \"mysql.slowlog.current_user\",\n" +
                "            \"mysql.slowlog.last_errno\",\n" +
                "            \"mysql.slowlog.killed\",\n" +
                "            \"mysql.slowlog.log_slow_rate_type\",\n" +
                "            \"mysql.slowlog.log_slow_rate_limit\",\n" +
                "            \"mysql.slowlog.innodb.trx_id\",\n" +
                "            \"nginx.access.remote_ip\",\n" +
                "            \"nginx.access.user_name\",\n" +
                "            \"nginx.access.method\",\n" +
                "            \"nginx.access.url\",\n" +
                "            \"nginx.access.http_version\",\n" +
                "            \"nginx.access.referrer\",\n" +
                "            \"nginx.access.agent\",\n" +
                "            \"nginx.access.user_agent.device\",\n" +
                "            \"nginx.access.user_agent.patch\",\n" +
                "            \"nginx.access.user_agent.name\",\n" +
                "            \"nginx.access.user_agent.os\",\n" +
                "            \"nginx.access.user_agent.os_name\",\n" +
                "            \"nginx.access.geoip.continent_name\",\n" +
                "            \"nginx.access.geoip.country_iso_code\",\n" +
                "            \"nginx.access.geoip.region_name\",\n" +
                "            \"nginx.access.geoip.city_name\",\n" +
                "            \"nginx.access.geoip.region_iso_code\",\n" +
                "            \"nginx.error.level\",\n" +
                "            \"nginx.error.message\",\n" +
                "            \"osquery.result.name\",\n" +
                "            \"osquery.result.action\",\n" +
                "            \"osquery.result.host_identifier\",\n" +
                "            \"osquery.result.calendar_time\",\n" +
                "            \"postgresql.log.timestamp\",\n" +
                "            \"postgresql.log.timezone\",\n" +
                "            \"postgresql.log.user\",\n" +
                "            \"postgresql.log.database\",\n" +
                "            \"postgresql.log.level\",\n" +
                "            \"postgresql.log.query\",\n" +
                "            \"postgresql.log.message\",\n" +
                "            \"redis.log.role\",\n" +
                "            \"redis.log.level\",\n" +
                "            \"redis.log.message\",\n" +
                "            \"redis.slowlog.cmd\",\n" +
                "            \"redis.slowlog.key\",\n" +
                "            \"redis.slowlog.args\",\n" +
                "            \"system.auth.timestamp\",\n" +
                "            \"system.auth.hostname\",\n" +
                "            \"system.auth.program\",\n" +
                "            \"system.auth.message\",\n" +
                "            \"system.auth.user\",\n" +
                "            \"system.auth.ssh.event\",\n" +
                "            \"system.auth.ssh.method\",\n" +
                "            \"system.auth.ssh.signature\",\n" +
                "            \"system.auth.ssh.geoip.continent_name\",\n" +
                "            \"system.auth.ssh.geoip.city_name\",\n" +
                "            \"system.auth.ssh.geoip.region_name\",\n" +
                "            \"system.auth.ssh.geoip.country_iso_code\",\n" +
                "            \"system.auth.ssh.geoip.region_iso_code\",\n" +
                "            \"system.auth.sudo.error\",\n" +
                "            \"system.auth.sudo.tty\",\n" +
                "            \"system.auth.sudo.pwd\",\n" +
                "            \"system.auth.sudo.user\",\n" +
                "            \"system.auth.sudo.command\",\n" +
                "            \"system.auth.useradd.name\",\n" +
                "            \"system.auth.useradd.home\",\n" +
                "            \"system.auth.useradd.shell\",\n" +
                "            \"system.auth.groupadd.name\",\n" +
                "            \"system.syslog.timestamp\",\n" +
                "            \"system.syslog.hostname\",\n" +
                "            \"system.syslog.program\",\n" +
                "            \"system.syslog.pid\",\n" +
                "            \"system.syslog.message\",\n" +
                "            \"traefik.access.remote_ip\",\n" +
                "            \"traefik.access.user_name\",\n" +
                "            \"traefik.access.user_identifier\",\n" +
                "            \"traefik.access.method\",\n" +
                "            \"traefik.access.url\",\n" +
                "            \"traefik.access.http_version\",\n" +
                "            \"traefik.access.referrer\",\n" +
                "            \"traefik.access.agent\",\n" +
                "            \"traefik.access.user_agent.device\",\n" +
                "            \"traefik.access.user_agent.build\",\n" +
                "            \"traefik.access.user_agent.patch\",\n" +
                "            \"traefik.access.user_agent.name\",\n" +
                "            \"traefik.access.user_agent.os\",\n" +
                "            \"traefik.access.user_agent.os_name\",\n" +
                "            \"traefik.access.geoip.continent_name\",\n" +
                "            \"traefik.access.geoip.country_iso_code\",\n" +
                "            \"traefik.access.geoip.region_name\",\n" +
                "            \"traefik.access.geoip.city_name\",\n" +
                "            \"traefik.access.geoip.region_iso_code\",\n" +
                "            \"traefik.access.frontend_name\",\n" +
                "            \"traefik.access.backend_url\",\n" +
                "            \"netflow.type\",\n" +
                "            \"netflow.exporter.address\",\n" +
                "            \"netflow.source_mac_address\",\n" +
                "            \"netflow.post_destination_mac_address\",\n" +
                "            \"netflow.destination_mac_address\",\n" +
                "            \"netflow.post_source_mac_address\",\n" +
                "            \"netflow.interface_name\",\n" +
                "            \"netflow.interface_description\",\n" +
                "            \"netflow.sampler_name\",\n" +
                "            \"netflow.application_description\",\n" +
                "            \"netflow.application_name\",\n" +
                "            \"netflow.class_name\",\n" +
                "            \"netflow.wlan_ssid\",\n" +
                "            \"netflow.vr_fname\",\n" +
                "            \"netflow.metro_evc_id\",\n" +
                "            \"netflow.nat_pool_name\",\n" +
                "            \"netflow.p2p_technology\",\n" +
                "            \"netflow.tunnel_technology\",\n" +
                "            \"netflow.encrypted_technology\",\n" +
                "            \"netflow.observation_domain_name\",\n" +
                "            \"netflow.selector_name\",\n" +
                "            \"netflow.information_element_description\",\n" +
                "            \"netflow.information_element_name\",\n" +
                "            \"netflow.virtual_station_interface_name\",\n" +
                "            \"netflow.virtual_station_name\",\n" +
                "            \"netflow.sta_mac_address\",\n" +
                "            \"netflow.wtp_mac_address\",\n" +
                "            \"netflow.user_name\",\n" +
                "            \"netflow.application_category_name\",\n" +
                "            \"netflow.application_sub_category_name\",\n" +
                "            \"netflow.application_group_name\",\n" +
                "            \"netflow.dot1q_customer_source_mac_address\",\n" +
                "            \"netflow.dot1q_customer_destination_mac_address\",\n" +
                "            \"netflow.mib_context_name\",\n" +
                "            \"netflow.mib_object_name\",\n" +
                "            \"netflow.mib_object_description\",\n" +
                "            \"netflow.mib_object_syntax\",\n" +
                "            \"netflow.mib_module_name\",\n" +
                "            \"netflow.mobile_imsi\",\n" +
                "            \"netflow.mobile_msisdn\",\n" +
                "            \"netflow.http_request_method\",\n" +
                "            \"netflow.http_request_host\",\n" +
                "            \"netflow.http_request_target\",\n" +
                "            \"netflow.http_message_version\",\n" +
                "            \"netflow.http_user_agent\",\n" +
                "            \"netflow.http_content_type\",\n" +
                "            \"netflow.http_reason_phrase\",\n" +
                "            \"iptables.fragment_flags\",\n" +
                "            \"iptables.input_device\",\n" +
                "            \"iptables.output_device\",\n" +
                "            \"iptables.tcp.flags\",\n" +
                "            \"iptables.ubiquiti.input_zone\",\n" +
                "            \"iptables.ubiquiti.output_zone\",\n" +
                "            \"iptables.ubiquiti.rule_number\",\n" +
                "            \"iptables.ubiquiti.rule_set\",\n" +
                "            \"suricata.eve.event_type\",\n" +
                "            \"suricata.eve.app_proto_orig\",\n" +
                "            \"suricata.eve.tcp.tcp_flags\",\n" +
                "            \"suricata.eve.tcp.tcp_flags_tc\",\n" +
                "            \"suricata.eve.tcp.state\",\n" +
                "            \"suricata.eve.tcp.tcp_flags_ts\",\n" +
                "            \"suricata.eve.fileinfo.sha1\",\n" +
                "            \"suricata.eve.fileinfo.filename\",\n" +
                "            \"suricata.eve.fileinfo.state\",\n" +
                "            \"suricata.eve.fileinfo.sha256\",\n" +
                "            \"suricata.eve.fileinfo.md5\",\n" +
                "            \"suricata.eve.proto\",\n" +
                "            \"suricata.eve.dns.type\",\n" +
                "            \"suricata.eve.dns.rrtype\",\n" +
                "            \"suricata.eve.dns.rrname\",\n" +
                "            \"suricata.eve.dns.rdata\",\n" +
                "            \"suricata.eve.dns.rcode\",\n" +
                "            \"suricata.eve.flow_id\",\n" +
                "            \"suricata.eve.email.status\",\n" +
                "            \"suricata.eve.http.redirect\",\n" +
                "            \"suricata.eve.http.http_user_agent\",\n" +
                "            \"suricata.eve.http.protocol\",\n" +
                "            \"suricata.eve.http.http_refer\",\n" +
                "            \"suricata.eve.http.url\",\n" +
                "            \"suricata.eve.http.hostname\",\n" +
                "            \"suricata.eve.http.http_method\",\n" +
                "            \"suricata.eve.http.http_content_type\",\n" +
                "            \"suricata.eve.in_iface\",\n" +
                "            \"suricata.eve.alert.category\",\n" +
                "            \"suricata.eve.alert.signature\",\n" +
                "            \"suricata.eve.alert.action\",\n" +
                "            \"suricata.eve.ssh.client.proto_version\",\n" +
                "            \"suricata.eve.ssh.client.software_version\",\n" +
                "            \"suricata.eve.ssh.server.proto_version\",\n" +
                "            \"suricata.eve.ssh.server.software_version\",\n" +
                "            \"suricata.eve.tls.issuerdn\",\n" +
                "            \"suricata.eve.tls.sni\",\n" +
                "            \"suricata.eve.tls.version\",\n" +
                "            \"suricata.eve.tls.fingerprint\",\n" +
                "            \"suricata.eve.tls.serial\",\n" +
                "            \"suricata.eve.tls.subject\",\n" +
                "            \"suricata.eve.app_proto_ts\",\n" +
                "            \"suricata.eve.flow.state\",\n" +
                "            \"suricata.eve.flow.reason\",\n" +
                "            \"suricata.eve.app_proto\",\n" +
                "            \"suricata.eve.app_proto_tc\",\n" +
                "            \"suricata.eve.smtp.rcpt_to\",\n" +
                "            \"suricata.eve.smtp.mail_from\",\n" +
                "            \"suricata.eve.smtp.helo\",\n" +
                "            \"suricata.eve.app_proto_expected\",\n" +
                "            \"fields.*\"\n" +
                "          ]\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "}", XContentType.JSON);
        AcknowledgedResponse putTemplateResponse = client.indices().putTemplate(request, RequestOptions.DEFAULT);
        System.out.println(putTemplateResponse);
    }

    /**
     * 验证当前模板是否存在
     */
    @Ignore
    @Test
    public void validateTemplete() throws IOException {
        IndexTemplatesExistRequest request = new IndexTemplatesExistRequest("shuyuq-test");
        request.setMasterNodeTimeout(TimeValue.timeValueMinutes(1));
        request.setMasterNodeTimeout("1m");
        request.setLocal(true);
        boolean exists = client.indices().existsTemplate(request, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    /**
     * 生成index
     *
     * @throws IOException
     */
    @Ignore
    @Test
    public void createEsIndexTest() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest("shuyuq-test-20200907");
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

        CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
        System.out.println(createIndexResponse);
    }

    /**
     * 查询index信息
     *
     * @throws IOException
     */
    @Test
    @Ignore
    public void getIndexTest() throws IOException {
        GetIndexRequest request = new GetIndexRequest("shuyuq-test-20200907");
        request.includeDefaults(true);
        GetIndexResponse getIndexResponse = client.indices().get(request, RequestOptions.DEFAULT);
        String type = getIndexResponse.getMappings().get("shuyuq-test-20200907").type();
        System.out.println(getIndexResponse);
    }

    /**
     * 日志推送
     */
    @Test
    @Ignore
    public void pushMessage() {
        request = new IndexRequest(
                "shuyuq-test-20200907",
                "_doc",
                "2");
        String jsonString = "{" +
                "\"user\":\"test\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";
        try {
            request.source(jsonString, XContentType.JSON);
            IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
            System.out.println(indexResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 根据数据Id查询数据信息
     */
    @Ignore
    @Test
    public void getMessageByDocId() throws IOException {
        GetRequest request = new GetRequest(
                "shuyuq-test-20200907",
                "_doc",
                "1");
//        request.storedFields("message");
        GetResponse getResponse = client.get(request, RequestOptions.DEFAULT);
        Map<String, Object> message = getResponse.getSource();
        System.out.println(message);
    }

    /**
     * 多结果查询
     */
    @Ignore
    @Test
    public void multiResultSearch() throws IOException {
        MultiSearchRequest request = new MultiSearchRequest();
        SearchRequest firstSearchRequest = new SearchRequest("shuyuq-test-20200907");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("user", "shuyuq"));
        firstSearchRequest.source(searchSourceBuilder);
        request.add(firstSearchRequest);
        SearchRequest secondSearchRequest = new SearchRequest("shuyuq-test-20200907");
        searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("message", "trying out Elasticsearch"));
        secondSearchRequest.source(searchSourceBuilder);
        request.add(secondSearchRequest);
        MultiSearchResponse response = client.msearch(request, RequestOptions.DEFAULT);
        System.out.println(response);
    }

    /**
     * 多条件查询
     */
    @Test
    @Ignore
    public void multiConditonSearch() throws IOException {
        EsCondition condition = new EsCondition();
        condition.setDate("20200924");
        Map<String, String> map = new HashMap<>();
        condition.setBeginIndex(0);
        condition.setSize(10);
        condition.setCondition(map);
        SearchResponse searchResponse = esUtil.searchEsLogByCondition(condition);
        String sourceString = searchResponse.getHits().getHits()[0].getSourceAsString();
        EsLog esLog = JsonUtil.getClassBean(sourceString, EsLog.class);
        System.out.println(esLog);
        System.out.println(searchResponse);
    }
}
