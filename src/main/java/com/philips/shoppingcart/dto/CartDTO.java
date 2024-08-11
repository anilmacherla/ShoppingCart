package com.philips.shoppingcart.dto;

import java.util.List;

public class CartDTO {
    private Long cartId;
    private List<CartItemDTO> items;

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long id) {
        this.cartId = id;
    }

    public List<CartItemDTO> getItems() {
        return items;
    }

    public void setItems(List<CartItemDTO> items) {
        this.items = items;
    }
}
