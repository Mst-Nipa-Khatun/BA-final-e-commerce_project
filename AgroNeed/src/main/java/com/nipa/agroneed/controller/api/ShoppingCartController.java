package com.nipa.agroneed.controller.api;

import com.nipa.agroneed.dto.IncrementDecrementShoppingCartDto;
import com.nipa.agroneed.dto.Response;
import com.nipa.agroneed.dto.ShoppingCartDto;
import com.nipa.agroneed.service.ShoppingCartService;
import com.nipa.agroneed.utils.UrlConstraint;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(UrlConstraint.ShoppingCart.ROOT)
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }
    @Secured("ROLE_USER")
    @PostMapping(UrlConstraint.ShoppingCart.CREATE)
    public Response createShoppingCart(@RequestBody ShoppingCartDto shoppingCartDto) {
        return shoppingCartService.createShoppingCart(shoppingCartDto);
    }

    @PostMapping(UrlConstraint.ShoppingCart.INCREMENT_DECREMENT)
    public Response incrementDecrementShoppingCart(@RequestBody IncrementDecrementShoppingCartDto incrementDecrementShoppingCartDto) {
        return shoppingCartService.incrementDecrementShoppingCart(incrementDecrementShoppingCartDto);
    }
    @GetMapping(UrlConstraint.ShoppingCart.GET_ALL)
    public Response getAllShoppingCart() {
        return shoppingCartService.getAllShoppingCart();
    }
}
