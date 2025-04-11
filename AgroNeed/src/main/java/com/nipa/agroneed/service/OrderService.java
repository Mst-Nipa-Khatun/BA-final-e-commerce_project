package com.nipa.agroneed.service;

import com.nipa.agroneed.dto.PlaceOrderDto;
import com.nipa.agroneed.dto.Response;

public interface OrderService {
    Response placeOrder(PlaceOrderDto placeOrderDto);
    Response getAllOrders();
}
