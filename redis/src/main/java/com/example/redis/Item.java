package com.example.redis;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@RedisHash("item")
public class Item implements Serializable {
    @Id
    private Long id;
    private String name;
    private String description;
    private Integer price;
}
