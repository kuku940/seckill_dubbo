package cn.xiaoyu.seckill.service.redis.impl;

import cn.xiaoyu.seckill.service.redis.RedisService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author Roin_zhang
 * @date 2018/4/6 9:28
 */
@Service(version = "1.0.0")
public class RedisServiceImpl implements RedisService {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void remove(String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    @Override
    public void removePattern(String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0) {
            redisTemplate.delete(keys);
        }
    }

    @Override
    public void remove(String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    @Override
    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    @Override
    public Serializable get(String key) {
        Object result = null;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return (Serializable) result;
    }

    @Override
    public boolean set(String key, Serializable value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean set(String key, Serializable value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public <K,HK,HV> boolean setMap(K key, Map<HK, HV> map, Long expireTime) {
        HashOperations<K, HK, HV> operations = redisTemplate.opsForHash();
        operations.putAll(key, map);

        if (expireTime != null) {
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
        }
        return false;
    }


    @Override
    public <K,HK,HV> Map<HK,HV> getMap(final K key) {
        HashOperations<K, HK, HV> operations = redisTemplate.opsForHash();
        return operations.entries(key);
    }
}
