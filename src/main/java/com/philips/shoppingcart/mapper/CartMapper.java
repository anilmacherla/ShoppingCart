package com.philips.shoppingcart.mapper;

import com.philips.shoppingcart.dto.CartDTO;
import com.philips.shoppingcart.dto.CartItemDTO;
import com.philips.shoppingcart.model.Cart;
import com.philips.shoppingcart.model.CartItem;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CartMapper {

    public CartDTO toCartDTO(Cart cart) {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setCartId(cart.getCartId());
        cartDTO.setItems(cart.getItems().stream().map(this::toCartItemDTO).collect(Collectors.toList()));
        return cartDTO;
    }

    public CartItemDTO toCartItemDTO(CartItem item) {
        CartItemDTO itemDTO = new CartItemDTO();
        itemDTO.setId(item.getCartItemId());
        itemDTO.setName(item.getName());
        itemDTO.setPrice(item.getPrice());
        itemDTO.setQuantity(item.getQuantity());
        return itemDTO;
    }
}
