package com.philips.shoppingcart.service;

import com.philips.shoppingcart.dto.ItemDTO;
import com.philips.shoppingcart.model.Item;

import java.util.List;

public interface ItemService {
    Item addItem(ItemDTO itemDTO);
    List<Item> getItems();
    Item getItemById(Long id) throws Exception;
}
