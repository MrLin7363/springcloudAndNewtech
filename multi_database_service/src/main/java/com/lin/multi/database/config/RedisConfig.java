/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2022-2022. All rights reserved.
 */

package com.lin.multi.database.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.jcache.config.JCacheConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;


@Configuration
public class RedisConfig extends JCacheConfigurerSupport {
    @Value("${redis.jedis.cluster.nodes}")
    private String strClusters;

    @Value("${redis.jedis.cluster.password}")
    private String clusterPassword;

    @Value("${redis.jedis.cluster.timeout}")
    private String timeout;

    @Value("${redis.jedis.cluster.maxredirects}")
    private String maxredirects;

    @Value("${redis.jedis.cluster.pool.max-idle}")
    private String maxIdle;

    @Value("${redis.jedis.cluster.pool.max-total}")
    private String maxTotal;

    @Value("${redis.jedis.cluster.pool.max-wait-millis}")
    private String maxWaitMillis;

    @Value("${redis.jedis.cluster.pool.min-evictable-idle-time-millis}")
    private String minEvictableIdleTimeMillis;

    @Value("${redis.jedis.cluster.pool.num-tests-per-eviction-run}")
    private String numTestsPerEvictionRun;

    @Value("${redis.jedis.cluster.pool.time-between-eviction-runs-millis}")
    private String timeBetweenEvictionRunsMillis;

    @Value("${redis.jedis.cluster.pool.test-on-borrow}")
    private String testOnBorrow;

    @Value("${redis.jedis.cluster.pool.test-while-idle}")
    private String testWhileIdle;

    @Value("${redis.jedis.password}")
    private String password;

    @Value("${redis.jedis.hostName}")
    private String hostName;

    @Value("${redis.jedis.port}")
    private int port;

    private Set<RedisNode> getClusterNodes() {
        String[] clusters = strClusters.split(",");
        // 添加redis集群的节点
        Set<RedisNode> clusterNodes = new HashSet<RedisNode>();

        for (String node : clusters) {
            String[] nodes = StringUtils.split(node, ":");
            clusterNodes.add(new RedisNode(nodes[0], Integer.parseInt(nodes[1])));
        }
        return clusterNodes;
    }

    private RedisClusterConfiguration redisClusterConfiguration() {
        RedisClusterConfiguration redisClusterConfig = new RedisClusterConfiguration();
        redisClusterConfig.setClusterNodes(getClusterNodes());
        redisClusterConfig.setMaxRedirects(Integer.parseInt(maxredirects));
        return redisClusterConfig;
    }

    private JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(Integer.parseInt(maxIdle));
        jedisPoolConfig.setMaxTotal(Integer.parseInt(maxTotal));
        jedisPoolConfig.setMaxWaitMillis(Integer.parseInt(maxWaitMillis));
        jedisPoolConfig.setMinEvictableIdleTimeMillis(Integer.parseInt(minEvictableIdleTimeMillis));
        jedisPoolConfig.setNumTestsPerEvictionRun(Integer.parseInt(numTestsPerEvictionRun));
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(Integer.parseInt(timeBetweenEvictionRunsMillis));
        jedisPoolConfig.setTestOnBorrow(Boolean.parseBoolean(testOnBorrow));
        jedisPoolConfig.setTestWhileIdle(Boolean.parseBoolean(testWhileIdle));
        return jedisPoolConfig;
    }

    /**
     * 未知
     *
     * @return 未知
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        JedisConnectionFactory connectionFactory;
        if (!StringUtils.isEmpty(password)) {
            connectionFactory = new JedisConnectionFactory(jedisPoolConfig());
            connectionFactory.setHostName(hostName);
            connectionFactory.setPort(port);
            connectionFactory.setPassword(password);
        } else {
            connectionFactory = new JedisConnectionFactory(redisClusterConfiguration(), jedisPoolConfig());
            connectionFactory.setPassword(clusterPassword);
        }
        connectionFactory.setTimeout(Integer.parseInt(timeout));
        return connectionFactory;
    }

    /**
     * 未知
     *
     * @param redisConnectionFactory 未知
     * @return 未知
     */
    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, String> redis = new RedisTemplate<String, String>();
        redis.setConnectionFactory(redisConnectionFactory);
        redis.afterPropertiesSet();
        return redis;
    }
}

