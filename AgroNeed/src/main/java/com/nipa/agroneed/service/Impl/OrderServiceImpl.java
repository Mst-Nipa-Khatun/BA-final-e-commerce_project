package com.nipa.agroneed.service.Impl;

import com.nipa.agroneed.dto.PlaceOrderDto;
import com.nipa.agroneed.dto.Response;
import com.nipa.agroneed.entity.OrderItemsEntity;
import com.nipa.agroneed.entity.OrdersEntity;
import com.nipa.agroneed.entity.ProductsEntity;
import com.nipa.agroneed.entity.ShoppingCartEntity;
import com.nipa.agroneed.repository.OrderItemsRepository;
import com.nipa.agroneed.repository.OrderRepository;
import com.nipa.agroneed.repository.ProductsRepository;
import com.nipa.agroneed.repository.ShoppingCartRepository;
import com.nipa.agroneed.service.OrderService;
import com.nipa.agroneed.utils.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductsRepository productsRepository;
    private final OrderItemsRepository orderItemsRepository;

    public OrderServiceImpl(OrderRepository orderRepository, ShoppingCartRepository shoppingCartRepository, ProductsRepository productsRepository, OrderItemsRepository orderItemsRepository) {
        this.orderRepository = orderRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.productsRepository = productsRepository;
        this.orderItemsRepository = orderItemsRepository;
    }

    @Override
    public Response placeOrder(PlaceOrderDto placeOrderDto) {
        List<ShoppingCartEntity> carts = shoppingCartRepository.findAllByUserIdAndStatus(placeOrderDto.getUserId(), 1);

        if (carts.isEmpty()) {
            return ResponseBuilder.getFailResponse(HttpStatus.BAD_REQUEST, null, "No items in cart for this user.");
        }

        double totalPrice = 0.0;
        int totalNumberOfProducts = 0;

        for (ShoppingCartEntity cart : carts) {
            ProductsEntity product = productsRepository.findByIdAndStatus(cart.getProductId(), 1);
            if (product != null) {
                totalPrice += product.getPrice() * cart.getQuantity();
                totalNumberOfProducts++;
            }
        }

        OrdersEntity order = new OrdersEntity();
        order.setUserId(placeOrderDto.getUserId());
        order.setStatus(1);
        order.setTotalPrice(totalPrice);
        order.setNumberOfProducts(totalNumberOfProducts);
        OrdersEntity savedOrder = orderRepository.save(order);

        carts.forEach(v ->
                v.setStatus(4));
        List<ShoppingCartEntity> updatedCarts = shoppingCartRepository.saveAll(carts); //akhon shooping cart e pick kora gulo 4 status


        List<OrderItemsEntity> orderItems = new ArrayList<>();
        for (ShoppingCartEntity shoppingCartart : updatedCarts) {
            ProductsEntity product = productsRepository.findByIdAndStatus(shoppingCartart.getProductId(), 1);
            if (product != null) {
                OrderItemsEntity orderItem = new OrderItemsEntity();
                orderItem.setOrderId(savedOrder.getId());
                orderItem.setProductId(product.getId());
                orderItem.setPrice(product.getPrice());
                orderItem.setQuantity(shoppingCartart.getQuantity());
                orderItem.setStatus(1);
                orderItems.add(orderItem);
            }
        }
        orderItemsRepository.saveAll(orderItems);

        return ResponseBuilder.getSuccessResponse(HttpStatus.OK, savedOrder, "Order placed successfully.");
    }

}
