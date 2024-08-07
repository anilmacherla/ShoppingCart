package com.philips.shoppingcart.service;

import com.philips.shoppingcart.dto.CartDTO;
import com.philips.shoppingcart.dto.CartItemDTO;
import com.philips.shoppingcart.model.Cart;

import java.util.List;

public interface CartService {
    Cart addItemToUserCart(Long userId, CartItemDTO itemDTO);
    Cart addItemsToUserCart(Long userId, List<CartItemDTO> itemDTOs);
    CartDTO getCartByUserId(Long userId) throws Exception;
}
