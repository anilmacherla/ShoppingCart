package com.philips.shoppingcart.service.impl;

import com.philips.shoppingcart.dto.ItemDTO;
import com.philips.shoppingcart.model.Item;
import com.philips.shoppingcart.repository.ItemRepository;
import com.philips.shoppingcart.service.ItemService;
import com.philips.shoppingcart.mapper.ItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemMapper itemMapper;

    @Override
    public Item addItem(ItemDTO itemDTO) {
        Item item = itemMapper.toItem(itemDTO);
        return itemRepository.save(item);
    }

    @Override
    public List<Item> getItems() {
        return itemRepository.findAll();
    }

    @Override
    public Item getItemById(Long id) throws Exception {
        return itemRepository.findById(id)
                .orElseThrow(() -> new Exception("Item not found with id: " + id));
    }
}
