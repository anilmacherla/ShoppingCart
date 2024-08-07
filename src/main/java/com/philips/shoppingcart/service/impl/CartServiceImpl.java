package com.philips.shoppingcart.service.impl;

import com.philips.shoppingcart.dto.CartDTO;
import com.philips.shoppingcart.dto.CartItemDTO;
import com.philips.shoppingcart.exception.ItemNotFoundException;
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
    public Cart addItemsToUserCart(Long userId, List<CartItemDTO> itemDTOs) {
        Cart cart = createCartForUser(userId); // Always create a new cart

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
    public CartDTO getCartByUserId(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ItemNotFoundException("No cart found for user ID: " + userId));

        return cartMapper.toCartDTO(cart);
    }

    @Override
    public CartDTO getCartByUserIdAndCartId(Long userId, Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ItemNotFoundException("No cart found with ID: " + cartId));

        if (!cart.getUserId().equals(userId)) {
            throw new ItemNotFoundException("No cart found for user ID: " + userId + " and cart ID: " + cartId);
        }

        return cartMapper.toCartDTO(cart);
    }

    @Override
    public Cart updateCartItem(Long cartId, Long itemId, CartItemDTO itemDTO) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ItemNotFoundException("No cart found with ID: " + cartId));

        CartItem item = cart.getItems().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new ItemNotFoundException("No item found with ID: " + itemId));

        item.setName(itemDTO.getName());
        item.setPrice(itemDTO.getPrice());
        item.setQuantity(itemDTO.getQuantity());

        return cartRepository.save(cart);
    }

    @Override
    public void deleteCartItem(Long cartId, Long itemId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ItemNotFoundException("No cart found with ID: " + cartId));

        CartItem item = cart.getItems().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new ItemNotFoundException("No item found with ID: " + itemId));

        cart.removeItem(item);
        cartRepository.save(cart);
    }
}
