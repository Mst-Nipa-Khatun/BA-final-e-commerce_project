package com.nipa.agroneed.service;

import com.nipa.agroneed.dto.Response;
import com.nipa.agroneed.dto.ShoppingCartDto;

public interface ShoppingCartService {
    Response createShoppingCart(ShoppingCartDto shoppingCartDto);
}
