package com.micropos.carts.mapper;

import com.micropos.carts.model.Cart;
import com.micropos.carts.model.Item;
import com.micropos.dto.CartDto;
import com.micropos.dto.CartItemDto;
import com.micropos.dto.ProductDto;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Mapper
public interface CartMapper {

    Collection<CartDto> toCartsDto(Collection<Cart> carts);

    Collection<Cart> toCarts(Collection<CartDto> carts);

    default Cart toCart(CartDto cartDto) {
        return new Cart().id(cartDto.getId())
                .items(toItems(cartDto.getItems(), cartDto));
    }

    default CartDto toCartDto(Cart cart) {
        return new CartDto().id(cart.id())
                .items(toItemDtos(cart.items()));
    }

    default List<CartItemDto> toItemDtos(List<Item> items) {
        if (items == null || items.isEmpty()) {
            return null;
        }
        List<CartItemDto> list = new ArrayList<>(items.size());
        for (Item item : items) {
            list.add(toItemDto(item));
        }

        return list;
    }

    default List<Item> toItems(List<CartItemDto> itemDtos, CartDto cartDto) {
        if (itemDtos == null || itemDtos.isEmpty()) {
            return null;
        }
        List<Item> list = new ArrayList<>(itemDtos.size());
        for (CartItemDto itemDto : itemDtos) {
            list.add(toItem(itemDto, cartDto));
        }

        return list;
    }

    default Item toItem(CartItemDto itemDto, CartDto cartDto) {
        return new Item().id(itemDto.getId())
                .cartId(cartDto.getId())
                .productId(itemDto.getProduct().getId())
                .productName(itemDto.getProduct().getName())
                .quantity(itemDto.getAmount())
                .productPrice(itemDto.getProduct().getPrice());
    }

    default Item toItem(CartItemDto itemDto, Integer cartId) {
        return new Item().id(itemDto.getId())
                .cartId(cartId)
                .productId(itemDto.getProduct().getId())
                .productName(itemDto.getProduct().getName())
                .quantity(itemDto.getAmount())
                .productPrice(itemDto.getProduct().getPrice());
    }

    default CartItemDto toItemDto(Item item) {

        return new CartItemDto().id(item.id())
                .product(item2ProductDto(item))
                .amount(item.quantity());
    }

    default ProductDto item2ProductDto(Item item) {
        return new ProductDto().id(item.productId())
                .name(item.productName())
                .price(item.productPrice());
    }
}
