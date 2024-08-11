package com.philips.shoppingcart.dto;

import jakarta.validation.constraints.*;

public class CartItemDTO {
    private Long cartItemId;

    public CartItemDTO(Long cartItemId, String name, double price, int quantity) {
        this.cartItemId = cartItemId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public CartItemDTO(){}

    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotNull(message = "Price is mandatory")
    @Min(value = 0, message = "Price must be non-negative")
    private double price;
    @NotNull(message = "Quantity is mandatory")
    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;

    public Long getId() {
        return cartItemId;
    }

    public void setId(Long id) {
        this.cartItemId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
