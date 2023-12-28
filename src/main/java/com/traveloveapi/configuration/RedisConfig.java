package com.traveloveapi.configuration;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPooled;

@Configuration
@Data
public class RedisConfig {
    @Value("${redis.host}")
    private String host;
    @Value("${redis.port}")
    private int port;
    private JedisPool pool;
    private Jedis jedis;
    private JedisPooled pooled;

    @PostConstruct
    private void initConnection() {
        pool = new JedisPool(host, port);
        pooled = new JedisPooled(host, port);
        jedis = pool.getResource();
    }
}
