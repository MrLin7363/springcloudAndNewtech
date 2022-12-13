/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2022-2022. All rights reserved.
 */

package com.lin.multi.database.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class RedisUtils {
    private static final String PREFIX = "com.lin:";

    private static final String DEFAULT_LOCK_KEY = "com.lin.lock:";

    @Autowired
    private StringRedisTemplate redisTemplate;

    // prod / dev+test 区分
    private String password;

    /**
     * lock
     *
     * @param key key
     * @param expire expire  ms
     * @return boolean
     */
    public Boolean lock(String key, long expire) {
        String lockKey = DEFAULT_LOCK_KEY + key;
        return redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            long expaireAt = System.currentTimeMillis() + expire + 1;
            Boolean acquire = connection.setNX(lockKey.getBytes(StandardCharsets.UTF_8),
                String.valueOf(expaireAt).getBytes(StandardCharsets.UTF_8));
            if (acquire) {
                return Boolean.TRUE;
            } else {
                byte[] value = connection.get(lockKey.getBytes(StandardCharsets.UTF_8));
                if (Objects.nonNull(value) && value.length > 0) {
                    long expaireTime = Long.parseLong(new String(value));
                    if (expaireTime < System.currentTimeMillis()) {
                        byte[] oldValue = connection.getSet(lockKey.getBytes(StandardCharsets.UTF_8),
                            String.valueOf(System.currentTimeMillis() + expire + 1).getBytes(StandardCharsets.UTF_8));
                        return Long.parseLong(new String(oldValue)) < System.currentTimeMillis();
                    }
                }
            }
            return Boolean.FALSE;
        });
    }


    /**
     * expire
     *
     * @param key key
     * @param timeout timeout
     */
    public void expire(String key, long timeout) {
        redisTemplate.expire(PREFIX + key, timeout, TimeUnit.MINUTES);
    }

    /**
     * increment
     *
     * @param key key
     * @param count count
     */
    public void increment(String key, int count) {
        redisTemplate.opsForValue().increment(PREFIX + key, count);
    }

    /**
     * existsPrefixKey
     *
     * @param key key
     * @return boolean
     */
    public boolean existsPrefixKey(String key) {
        return redisTemplate.hasKey(PREFIX + key);
    }

    /**
     * addHyperLog
     *
     * @param key key
     * @param value value
     */
    public void addHyperLog(String key, String value) {
        redisTemplate.opsForHyperLogLog().add(PREFIX + key, value);
    }

    /**
     * unionHyperLog
     *
     * @param key key
     * @param keys keys
     */
    public void unionHyperLog(String key, String... keys) {
        redisTemplate.opsForHyperLogLog().union(PREFIX + key, keys);
    }

    /**
     * countHyperLog
     *
     * @param key key
     * @return long
     */
    public long countHyperLog(String key) {
        return redisTemplate.opsForHyperLogLog().size(key);
    }

    /**
     * getPatternKeys
     *
     * @param pattern pattern
     * @param count suggest 1000
     * @return Set<String>
     */
    public Set<String> getPatternKeys(String pattern, int count) {
        if (!StringUtils.isEmpty(password)) {
            // 单机
            // match different from java
            return redisTemplate.execute((RedisCallback<Set<String>>) connection -> {
                ScanOptions scanOptions = ScanOptions.scanOptions().match(pattern).count(count).build();
                Set<String> result = new HashSet<>();
                try {
                    Cursor<byte[]> cursor = connection.scan(scanOptions);
                    while (cursor.hasNext()) {
                        byte[] next = cursor.next();
                        result.add(new String(next, StandardCharsets.UTF_8));
                    }
                } catch (Exception e) {
                }
                return result;
            });
        } else {
            // 集群
            long begin = System.currentTimeMillis();
            Set<String> patternKeysCluster = getPatternKeysCluster(pattern, count);
            System.out.println("total"+(System.currentTimeMillis()-begin));
            return patternKeysCluster;
        }
    }

    private Set<String> getPatternKeysCluster(String matchKey, int count) {
        Set<String> result = new HashSet<>();
        Map<String, JedisPool> clusterNodes = ((JedisCluster) redisTemplate.getConnectionFactory()
            .getClusterConnection().getNativeConnection()).getClusterNodes();
        for (Map.Entry<String, JedisPool> entry : clusterNodes.entrySet()) {
            Jedis jedis = entry.getValue().getResource();
            if (!jedis.info("replication").contains("role:slave")) {
                long begin = System.currentTimeMillis();
                List<String> keys = getScan(jedis, matchKey, count);
                System.out.println(System.currentTimeMillis()-begin);
                if (keys.size() > 0) {
                    result.addAll(keys);
                }
            }
        }
        return result;
    }

    private List<String> getScan(Jedis jedis, String key, int count) {
        List<String> result = new ArrayList<>();
        ScanParams params = new ScanParams().match(key).count(count);
        String cursor = "0";
        ScanResult scanResult = jedis.scan(cursor, params);

        while (scanResult.getStringCursor() != null) {
            result.addAll(scanResult.getResult());
            String nextCursor = scanResult.getStringCursor();
            if (!"0".equals(nextCursor)) {
                scanResult = jedis.scan(nextCursor, params);
            } else {
                break;
            }
        }
        return result;
    }

    /**
     * expireFullName
     *
     * @param key key
     * @param timeout timeout
     */
    public void expireFullName(String key, long timeout) {
        redisTemplate.expire(key, timeout, TimeUnit.MINUTES);
    }

    /**
     * deleteFullName
     *
     * @param key key
     */
    public void deleteFullName(String key) {
        redisTemplate.delete(key);
    }

    /**
     * getFullValue
     *
     * @param key key
     * @return String
     */
    public String getFullValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * add set
     *
     * @param key key
     * @param values values
     */
    public void addSet(String key, String... values) {
        redisTemplate.opsForSet().add(PREFIX + key, values);
    }

    /**
     * getSet
     *
     * @param key key
     * @return Set<String>
     */
    public Set<String> getSet(String key) {
        return redisTemplate.opsForSet().members(PREFIX + key);
    }

    /**
     * addZSet
     *
     * @param key key
     * @param set set
     */
    public void addZSet(String key, Set set) {
        redisTemplate.opsForZSet().add(PREFIX + key, set);
    }

    /**
     * rangeZSet
     *
     * @param key key
     * @param min min
     * @param max max
     * @return Set
     */
    public Set rangeZSet(String key, double min, double max) {
        return redisTemplate.opsForZSet().rangeByScoreWithScores(PREFIX + key, min, max);
    }
}
