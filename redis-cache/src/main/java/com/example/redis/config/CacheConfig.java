package com.example.redis.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;

@Configuration
@EnableCaching
public class CacheConfig {
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration configuration = RedisCacheConfiguration
                // 기본 설정들을 사용한다.
                .defaultCacheConfig()
                // null은 캐시하지 않는다.
                .disableCachingNullValues()
                // 기본 캐시 유지 시간
                .entryTtl(Duration.ofSeconds(120)) // 10초
                // 캐시를 구분하는 접두사 설정
                // 캐시가 Redis에 들어갈텐데, key의 모습이 어떤 식으로 될까 => 이 상황에서 기본설정을 가져간다.
                .computePrefixWith(CacheKeyPrefix.simple())
                // value를 어떤 방법으로 직렬화 할지
                // java 바이트 코드로 직렬화 해서 Redis에 데이터를 올림
                // 캐시에 저장할 값을 어떻게 직렬화/역직렬화 할 것인지
                .serializeValuesWith(SerializationPair.fromSerializer(RedisSerializer.java()));

        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(configuration)
                .build();

    }
}
