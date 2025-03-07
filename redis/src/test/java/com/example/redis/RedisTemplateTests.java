package com.example.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@SpringBootTest
public class RedisTemplateTests {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void stringOpsTest(){
        // 문자열 조작을 위한 클래스
        ValueOperations<String,String> ops = stringRedisTemplate.opsForValue();
        // RedisTemplate에 설정된 타입을 바탕으로 Redis 문자열 조작
        ops.set("simpleKey", "simpleValue");
        System.out.println(ops.get("simpleKey"));

        SetOperations<String, String> setOps = stringRedisTemplate.opsForSet();
        setOps.add("setKey", "set1", "set2", "set3");
        System.out.println(setOps.size("setKey"));
    }

    @Autowired
    private RedisTemplate<String, ItemDto> itemRedisTemplate;

    @Test
    public void itemRedisTemplateTest(){
        ValueOperations<String, ItemDto> ops = itemRedisTemplate.opsForValue();
        ops.set("my:keyboard",ItemDto.builder().price(100000).name("Keyboard").description("OMG").build());
        System.out.println(ops.get("my:keyboard"));

    }
}
