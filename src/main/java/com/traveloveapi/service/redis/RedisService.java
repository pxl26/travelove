package com.traveloveapi.service.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPooled;

@Service
public class RedisService {
    @Value("${redis.host}")
    private String host;
    private JedisPooled jedis = new JedisPooled(host, 6379);

    public JedisPooled getConnection() {
        return jedis;
    }

}
