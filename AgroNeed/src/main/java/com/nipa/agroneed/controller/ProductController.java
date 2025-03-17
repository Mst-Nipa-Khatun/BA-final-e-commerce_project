package com.nipa.agroneed.controller;

import com.nipa.agroneed.dto.Response;
import com.nipa.agroneed.dto.SelectedProductsDto;
import com.nipa.agroneed.service.ProductsService;
import com.nipa.agroneed.utils.UrlConstraint;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(UrlConstraint.Products.ROOT)

public class ProductController {
    private final ProductsService productsService;


    public ProductController(ProductsService productsService) {
        this.productsService = productsService;
    }
    @PostMapping(UrlConstraint.Products.CREATE)
    public Response addProducts(@RequestBody SelectedProductsDto selectedProductsDto){
        return productsService.addProducts(selectedProductsDto);
    }
}
