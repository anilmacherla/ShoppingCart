package com.philips.shoppingcart.controller;

import com.philips.shoppingcart.dto.CartDTO;
import com.philips.shoppingcart.dto.CartItemDTO;
import com.philips.shoppingcart.model.Cart;
import com.philips.shoppingcart.service.CartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class ShoppingCartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/{userId}/addItems")
    @ResponseStatus(HttpStatus.OK)
    public Cart addItemsToCart(@PathVariable Long userId, @Valid @RequestBody List<@Valid CartItemDTO> itemDTOs) {
        return cartService.addItemsToUserCart(userId, itemDTOs);
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public CartDTO getCartByUserId(@PathVariable Long userId) {
        return cartService.getCartByUserId(userId);
    }

    @GetMapping("/{userId}/cart/{cartId}")
    @ResponseStatus(HttpStatus.OK)
    public CartDTO getCartByUserIdAndCartId(@PathVariable Long userId, @PathVariable Long cartId) throws Exception {
        return cartService.getCartByUserIdAndCartId(userId, cartId);
    }

}
