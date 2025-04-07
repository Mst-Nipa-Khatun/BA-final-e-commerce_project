package com.nipa.agroneed.service.Impl;

import com.nipa.agroneed.dto.Response;
import com.nipa.agroneed.dto.ShoppingCartDto;
import com.nipa.agroneed.entity.ProductsEntity;
import com.nipa.agroneed.entity.ShoppingCartEntity;
import com.nipa.agroneed.entity.User;
import com.nipa.agroneed.repository.ProductsRepository;
import com.nipa.agroneed.repository.ShoppingCartRepository;
import com.nipa.agroneed.repository.UserRepository;
import com.nipa.agroneed.service.ShoppingCartService;
import com.nipa.agroneed.utils.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;
    private final ProductsRepository productsRepository;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository, UserRepository userRepository, ProductsRepository productsRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.userRepository = userRepository;
        this.productsRepository = productsRepository;
    }

    @Override
    public Response createShoppingCart(ShoppingCartDto shoppingCartDto) {
        User user = userRepository.findByIdAndStatus(shoppingCartDto.getUserId(), 1);
        if (user == null) {
            return ResponseBuilder.getFailResponse(HttpStatus.BAD_REQUEST, null, "User not found");
        }
        ProductsEntity products = productsRepository.findByIdAndStatus(shoppingCartDto.getProductId(), 1);
        if (products == null) {
            return ResponseBuilder.getFailResponse(HttpStatus.BAD_REQUEST, null, "Product not found");
        }
        Integer stock = products.getStock();
        if (!(stock != null && stock > 0)) {
            return ResponseBuilder.getFailResponse(HttpStatus.BAD_REQUEST,
                    null, "Stock not enough");
        }
        ShoppingCartEntity shoppingCart =
                shoppingCartRepository.findByUserIdAndProductIdAndStatus(shoppingCartDto.getUserId(),
                        shoppingCartDto.getProductId(), 1);
        if (shoppingCart != null) {
            return ResponseBuilder.getFailResponse(HttpStatus.CONFLICT, null,
                    "Product already exists," +
                            "you increase or decrease your products");

        }
        shoppingCart = new ShoppingCartEntity();
        shoppingCart.setUserId(user.getId());
        shoppingCart.setStatus(1);
        shoppingCart.setProductId(shoppingCartDto.getProductId());
        shoppingCart.setQuantity(shoppingCartDto.getQuantity());
        ShoppingCartEntity savedCart = shoppingCartRepository.save(shoppingCart);
        return ResponseBuilder.getSuccessResponse(HttpStatus.CREATED, savedCart, "Products added in cart");
    }
}
