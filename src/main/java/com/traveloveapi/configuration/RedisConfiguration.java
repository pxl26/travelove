package com.traveloveapi.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPooled;

//@Component
public class RedisConfiguration {
    private JedisPooled jedis = new JedisPooled("localhost", 6379);

    public JedisPooled getConnection() {
        return jedis;
    }
}
