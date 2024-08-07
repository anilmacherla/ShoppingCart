package com.philips.shoppingcart.controller;

import com.philips.shoppingcart.dto.CartDTO;
import com.philips.shoppingcart.dto.CartItemDTO;
import com.philips.shoppingcart.model.Cart;
import com.philips.shoppingcart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class ShoppingCartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/{userId}/add")
    public Cart addItemToCart(@PathVariable Long userId, @RequestBody CartItemDTO itemDTO) {
        return cartService.addItemToUserCart(userId, itemDTO);
    }

    @PostMapping("/{userId}/addItems")
    public Cart addItemsToCart(@PathVariable Long userId, @RequestBody List<CartItemDTO> itemDTOs) {
        return cartService.addItemsToUserCart(userId, itemDTOs);
    }

    @GetMapping("/{userId}")
    public CartDTO getCartByUserId(@PathVariable Long userId) throws Exception {
        return cartService.getCartByUserId(userId);
    }
}
