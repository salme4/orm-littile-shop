package com.myhome.shop.ormlittileshop.domain.product.service;

import com.myhome.shop.ormlittileshop.domain.product.Item;
import com.myhome.shop.ormlittileshop.domain.product.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("itemService")
@Transactional
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    public List<Item> findAllItem() {
        return itemRepository.findAll();
    }

    public Item findOne(Long id) {
        return itemRepository.findById(id).get();
    }
}
