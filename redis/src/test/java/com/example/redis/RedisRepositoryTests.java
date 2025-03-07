package com.example.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RedisRepositoryTests {
    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void createTest() {
        Item item = Item.builder()
                .id(1L)
                .name("Test Item")
                .description("This is a test item")
                .price(200000)
                .build();
        itemRepository.save(item);
    }
    @Test
    public void readTest() {
        Item item = itemRepository.findById(1L).orElseThrow();
        System.out.println(item.getDescription());
    }

    @Test
    public void updateTest() {
        Item item = itemRepository.findById(1L).orElseThrow();
        item.setDescription("Updated description");
        itemRepository.save(item);
        System.out.println(item.getDescription());
    }

    @Test
    public void deleteTest() {
        itemRepository.deleteById(1L);
    }
}
