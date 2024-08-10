package com.philips.shoppingcart.service;

import com.philips.shoppingcart.dto.CartDTO;
import com.philips.shoppingcart.dto.CartItemDTO;
import com.philips.shoppingcart.model.Cart;

import java.util.List;


public interface CartService {
    Cart addItemsToUserCart(Long userId, List<CartItemDTO> itemDTOs);
    List<CartDTO> getCartListByUserId(Long userId);
    CartDTO getCartByUserIdAndCartId(Long userId, Long cartId) throws Exception;  
    Cart updateCartItem(Long cartId, Long itemId, CartItemDTO itemDTO) throws Exception;
    void deleteCartItem(Long cartId, Long itemId) throws Exception;
}

