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

    //product er  Creation API korte hoeb okhane seletecd categorioes dto thakeb with image url requestbody te thakbr
    //ei image er akhond db te empty prbe pore dynamically url porbe,
    //db te data okhon jabe same vabei productid and categories id o porbe

    private final ProductsService productsService;


    public ProductController(ProductsService productsService) {
        this.productsService = productsService;
    }
    @PostMapping(UrlConstraint.Products.CREATE)
    public Response addProducts(@RequestBody SelectedProductsDto selectedProductsDto){
        return productsService.addProducts(selectedProductsDto);
    }
}
