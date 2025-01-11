package com.warehouse.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.warehouse.common.facade.models.ims.ItemDetailsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    ObjectMapper mapper = new ObjectMapper();

    public <T> Optional<T> get(String key, TypeReference<T> typeReference) {
        try {
            if (redisTemplate.opsForValue().get(key) == null) {
                return Optional.empty();
            }
            return Optional.of(mapper.readValue(redisTemplate.opsForValue().get(key), typeReference));
        } catch (Exception e) {
            log.error("Exception occurred in redis()", e);
            return Optional.empty();
        }
    }

    public <T> Optional<T> get(String key, Class<T> clazz) {
        try {
            return Optional.of(mapper.readValue(redisTemplate.opsForValue().get(key), clazz));
        } catch (Exception e) {
            log.error("Exception occurred in redis()", e);
            return Optional.empty();
        }
    }

    public <T> void set(String key, T data, Long ttl) {
        try {
            redisTemplate.opsForValue().set(key, mapper.writeValueAsString(data), ttl, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("Exception occurred in redis()", e);
        }
    }

    public void delete(String key) {
        try {
            redisTemplate.opsForValue().getAndDelete(key);
        } catch (Exception e) {
            log.error("Exception occurred in redis()", e);
        }
    }
}
