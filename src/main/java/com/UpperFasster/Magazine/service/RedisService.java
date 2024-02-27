package com.UpperFasster.Magazine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.Set;

@Service
public class RedisService {
    private final Jedis jedis;

    @Autowired
    public RedisService(Jedis jedis) {
        this.jedis = jedis;
    }

    public void setValue(String key, String value, int second) {
        jedis.set(key, value);
        jedis.expire(key, second);
    }

    public String getValue(String key) {
        return jedis.get(key);
    }

    public void delValue(String key) {
        jedis.del(key);
    }

    public Set<String> getAll() {
        return jedis.keys("*");
    }
}
