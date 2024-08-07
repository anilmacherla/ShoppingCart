package com.philips.shoppingcart.service.impl;

import com.philips.shoppingcart.dto.CartDTO;
import com.philips.shoppingcart.dto.CartItemDTO;
import com.philips.shoppingcart.mapper.CartMapper;
import com.philips.shoppingcart.model.Cart;
import com.philips.shoppingcart.model.CartItem;
import com.philips.shoppingcart.repository.CartRepository;
import com.philips.shoppingcart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartMapper cartMapper;

    private Cart createCartForUser(Long userId) {
        Cart cart = new Cart();
        cart.setUserId(userId);
        return cartRepository.save(cart);
    }

    @Override
    public Cart addItemToUserCart(Long userId, CartItemDTO itemDTO) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> createCartForUser(userId));

        CartItem item = new CartItem();
        item.setName(itemDTO.getName());
        item.setPrice(itemDTO.getPrice());
        item.setQuantity(itemDTO.getQuantity());
        cart.addItem(item);

        return cartRepository.save(cart);
    }

    @Override
    public Cart addItemsToUserCart(Long userId, List<CartItemDTO> itemDTOs) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> createCartForUser(userId));

        for (CartItemDTO itemDTO : itemDTOs) {
            CartItem item = new CartItem();
            item.setName(itemDTO.getName());
            item.setPrice(itemDTO.getPrice());
            item.setQuantity(itemDTO.getQuantity());
            cart.addItem(item);
        }

        return cartRepository.save(cart);
    }

    @Override
    public CartDTO getCartByUserId(Long userId) throws Exception {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new Exception("Cart not found for user id: " + userId));

        return cartMapper.toCartDTO(cart);
    }
}
