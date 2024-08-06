package com.philips.shoppingcart.mapper;

import com.philips.shoppingcart.dto.ItemDTO;
import com.philips.shoppingcart.model.Item;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper {
    public Item toItem(ItemDTO itemDTO) {
        Item item = new Item();
        item.setName(itemDTO.getName());
        item.setPrice(itemDTO.getPrice());
        item.setQuantity(itemDTO.getQuantity());
        return item;
    }

    public ItemDTO toItemDTO(Item item) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setName(item.getName());
        itemDTO.setPrice(item.getPrice());
        itemDTO.setQuantity(item.getQuantity());
        return itemDTO;
    }
}
