package com.example.redis;

import com.example.redis.domain.Item;
import com.example.redis.domain.ItemDto;
import com.example.redis.repo.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @CachePut(cacheNames = "itemCache", key = "#result.id")
    public ItemDto create(ItemDto dto) {
        return ItemDto.fromEntity(itemRepository.save(Item.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .build()));
    }

    @Cacheable(cacheNames = "itemAllCache", key = "getMethodName()")
    public List<ItemDto> readAll() {
        return itemRepository.findAll()
                .stream()
                .map(ItemDto::fromEntity)
                .toList();
    }

    // 이 메서드 결과는 캐싱이 가능
    @Cacheable(cacheNames = "itemCache", key = "args[0]")
    public ItemDto readOne(Long id) {
        log.info("Read one: {}", id);
        return itemRepository.findById(id)
                .map(ItemDto::fromEntity)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @CachePut(cacheNames = "itemCache", key = "args[0]")
    @CacheEvict(cacheNames = "itemAllCache", allEntries = true)
    public ItemDto update(Long id, ItemDto dto) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        item.setName(dto.getName());
        item.setDescription(dto.getDescription());
        item.setPrice(dto.getPrice());
        return ItemDto.fromEntity(itemRepository.save(item));
    }


    //    @CacheEvict(cacheNames = {"itemCache","itemAllCache"}, allEntries = true)
    @Caching(evict = {
            @CacheEvict(cacheNames = "itemCache", key = "args[0]"),
            @CacheEvict(cacheNames = "itemAllCache", allEntries = true)
    })
    public void delete(Long id) {
        itemRepository.deleteById(id);
    }

    @Cacheable(cacheNames = "itemSearchCache", key="{args[0],args[1].pageNumber,args[1].pageSize}")
    public Page<ItemDto> SearchByName(String query, Pageable pageable) {
        return itemRepository.findAllByNameContains(query,pageable)
                .map(ItemDto::fromEntity);
    }

}
