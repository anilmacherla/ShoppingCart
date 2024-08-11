package com.philips.shoppingcart.controller;

import com.philips.shoppingcart.dto.CartDTO;
import com.philips.shoppingcart.dto.CartItemDTO;
import com.philips.shoppingcart.exception.GlobalExceptionHandler;
import com.philips.shoppingcart.exception.ItemNotFoundException;
import com.philips.shoppingcart.model.Cart;
import com.philips.shoppingcart.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ShoppingCartControllerTest {

    @InjectMocks
    private ShoppingCartController shoppingCartController;
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CartService cartService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(shoppingCartController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }


    @Test
    void testAddItemsToCart() throws Exception {
        Long userId = 1L;
        Cart cart = new Cart();
        cart.setUserId(userId);

        when(cartService.addItemsToUserCart(eq(userId), anyList())).thenReturn(cart);

        mockMvc.perform(post("/api/cart/{userId}/addItems", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[{\"name\": \"Item 1\", \"price\": 10.99, \"quantity\": 2}, {\"name\": \"Item 2\", \"price\": 5.49, \"quantity\": 3}]"))
                .andExpect(status().isOk())
                .andExpect(content().json("{'userId':1,'items':[]}"));

        verify(cartService, times(1)).addItemsToUserCart(eq(userId), anyList());
    }

    @Test
    void testGetCartByUserId() throws Exception {
        Long userId = 1L;
        CartDTO cartDTO = new CartDTO();
        cartDTO.setCartId(1L);
        CartItemDTO item1 = new CartItemDTO(1L, "Item 4", 10.99, 2);
        CartItemDTO item2 = new CartItemDTO(2L, "Item 5", 5.49, 3);
        CartItemDTO item3 = new CartItemDTO(3L, "Item 6", 7.99, 1);
        cartDTO.setItems(List.of(item1, item2, item3));
        when(cartService.getCartListByUserId(userId)).thenReturn(List.of(cartDTO));

        mockMvc.perform(get("/api/cart/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(content().json("[\n" +
                        "    {\n" +
                        "        \"cartId\": 1,\n" +
                        "        \"items\": [\n" +
                        "            {\n" +
                        "                \"name\": \"Item 4\",\n" +
                        "                \"price\": 10.99,\n" +
                        "                \"quantity\": 2,\n" +
                        "                \"id\": 1\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"name\": \"Item 5\",\n" +
                        "                \"price\": 5.49,\n" +
                        "                \"quantity\": 3,\n" +
                        "                \"id\": 2\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"name\": \"Item 6\",\n" +
                        "                \"price\": 7.99,\n" +
                        "                \"quantity\": 1,\n" +
                        "                \"id\": 3\n" +
                        "            }\n" +
                        "        ]\n" +
                        "    }\n" +
                        "]"));

        verify(cartService, times(1)).getCartListByUserId(userId);
    }

    @Test
    void testGetCartByUserId_NotFound() throws Exception {
        Long userId = 1L;

        when(cartService.getCartListByUserId(userId)).thenThrow(new ItemNotFoundException("No cart found for user ID: " + userId));

        mockMvc.perform(get("/api/cart/{userId}", userId))
                .andExpect(status().isNotFound())
                .andExpect(content().json("{\"message\":\"No cart found for user ID: 1\"}"));

        verify(cartService, times(1)).getCartListByUserId(userId);
    }

    @Test
    void testAddItemsToCart_InvalidInput() throws Exception {
        Long userId = 1L;

        mockMvc.perform(post("/api/cart/{userId}/addItems", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[{\"name\": \"\", \"price\": -10.99, \"quantity\": 0}]"))
                        .andExpect(status().is5xxServerError());

        verify(cartService, times(0)).addItemsToUserCart(anyLong(), anyList());
    }

    @Test
    void testGetCartByUserIdAndCartId() throws Exception {
        Long userId = 1L;
        Long cartId = 1L;
        CartDTO cartDTO = new CartDTO();

        when(cartService.getCartByUserIdAndCartId(userId, cartId)).thenReturn(cartDTO);

        mockMvc.perform(get("/api/cart/{userId}/cart/{cartId}", userId, cartId))
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));

        verify(cartService, times(1)).getCartByUserIdAndCartId(userId, cartId);
    }

    @Test
    void testGetCartByUserIdAndCartId_NotFound() throws Exception {
        Long userId = 1L;
        Long cartId = 1L;

        when(cartService.getCartByUserIdAndCartId(userId, cartId)).thenThrow(new ItemNotFoundException("No cart found for user ID: " + userId + " and cart ID: " + cartId));

        mockMvc.perform(get("/api/cart/{userId}/cart/{cartId}", userId, cartId))
                .andExpect(status().isNotFound())
                .andExpect(content().json("{\"message\":\"No cart found for user ID: 1 and cart ID: 1\"}"));

        verify(cartService, times(1)).getCartByUserIdAndCartId(userId, cartId);
    }
}