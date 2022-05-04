package com.micropos.carts.rest;

import com.micropos.api.CartsApi;
import com.micropos.carts.mapper.CartMapper;
import com.micropos.carts.model.Cart;
import com.micropos.carts.model.Item;
import com.micropos.carts.service.CartService;
import com.micropos.dto.CartDto;
import com.micropos.dto.CartItemDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CartController implements CartsApi {

    private CartService cartService;
    private CartMapper cartMapper;


    public CartController(CartService cartService,CartMapper cartMapper){
        this.cartService = cartService;
        this.cartMapper = cartMapper;
    }


    @Autowired
    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }

    @Override
    @PostMapping("/carts/{cartId}")
    public ResponseEntity<CartDto> addItemToCart(Integer cartId, CartItemDto cartItemDto) {
        Cart cart = cartService.getCart(cartId).orElse(null);
        if(cart == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Item item = cartMapper.toItem(cartItemDto, cartId);
        Cart ret = cartService.add(cart, item);
        if(ret == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(cartMapper.toCartDto(ret),HttpStatus.OK);
    }

    @Override
    @PostMapping("/carts")
    public ResponseEntity<CartDto> createCart(@RequestBody CartDto cartDto) {
        Cart ret = cartMapper.toCart(cartDto);

        if(ret == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ret = cartService.addCart(ret);

        if (ret == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cartMapper.toCartDto(ret),HttpStatus.OK);
    }

    @Override
    @GetMapping("/carts")
    public ResponseEntity<List<CartDto>> listCarts() {

        List<CartDto> carts = new ArrayList<>(cartMapper.toCartsDto(cartService.getAllCarts()));
        if(carts == null || carts.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(carts,HttpStatus.OK);
    }

    @Override
    @GetMapping("/carts/{cartId}")
    public ResponseEntity<CartDto> showCartById(@PathVariable("cartId") Integer cartId) {

        Cart cart = cartService.getCart(cartId).orElse(null);
        if(cart == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        CartDto cartDto = cartMapper.toCartDto(cart);
        return new ResponseEntity<>(cartDto,HttpStatus.OK);
    }

    @Override
    @GetMapping("/carts/{cartId}/total")
    public ResponseEntity<Double> showCartTotal(Integer cartId) {
        double totalAmount =  cartService.checkout(cartId);
        if(cartId == -1d)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(totalAmount);
    }
}
