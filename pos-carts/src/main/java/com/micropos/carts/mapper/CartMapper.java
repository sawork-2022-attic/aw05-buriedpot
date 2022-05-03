package com.micropos.carts.mapper;

import com.micropos.carts.model.Cart;
import com.micropos.carts.model.Item;
import com.micropos.dto.CartDto;
import com.micropos.dto.CartItemDto;
import org.mapstruct.Mapper;

import java.util.Collection;

@Mapper
public interface CartMapper {

    Collection<CartDto> toCartsDto(Collection<Cart> carts);

    Collection<Cart> toCarts(Collection<CartDto> carts);

    Cart toCart(CartDto cartDto);

    CartDto toCartDto(Cart cart);

    Item toItem(CartItemDto ItemDto);

    CartItemDto toItemDto(Item Item);
}
