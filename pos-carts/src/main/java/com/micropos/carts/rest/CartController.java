package com.micropos.carts.rest;

import com.micropos.api.CartsApi;
import com.micropos.carts.service.CartService;
import com.micropos.dto.CartDto;
import com.micropos.dto.CartItemDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CartController implements CartsApi {

    private CartService cartService;

    @Autowired
    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }

    @Override
    public ResponseEntity<CartDto> addItemToCart(Integer cartId, CartItemDto cartItemDto) {
        return CartsApi.super.addItemToCart(cartId, cartItemDto);
    }

    @Override
    public ResponseEntity<CartDto> createCart(CartDto cartDto) {
        return CartsApi.super.createCart(cartDto);
    }

    @Override
    public ResponseEntity<List<CartDto>> listCarts() {
        return CartsApi.super.listCarts();
    }

    @Override
    public ResponseEntity<CartDto> showCartById(Integer cartId) {
        return CartsApi.super.showCartById(cartId);
    }

    @Override
    public ResponseEntity<Double> showCartTotal(Integer cartId) {

        Double total = cartService.checkout(cartId);

        if (total == -1d) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(total);
    }
}
