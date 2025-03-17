package com.nipa.agroneed.service.Impl;

import com.nipa.agroneed.dto.Response;
import com.nipa.agroneed.dto.SelectedProductsDto;
import com.nipa.agroneed.entity.CategoriesEntity;
import com.nipa.agroneed.entity.ProductCategoriesEntity;
import com.nipa.agroneed.entity.ProductsEntity;
import com.nipa.agroneed.repository.CategoriesRepository;
import com.nipa.agroneed.repository.ProductsCategoriesRepository;
import com.nipa.agroneed.repository.ProductsRepository;
import com.nipa.agroneed.service.ProductsService;
import com.nipa.agroneed.utils.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ProductsServiceImpl implements ProductsService {
    private final ProductsRepository productsRepository;
    private final CategoriesRepository categoriesRepository;
    private final ProductsCategoriesRepository productsCategoriesRepository;


    public ProductsServiceImpl(ProductsRepository productsRepository, CategoriesRepository categoriesRepository, ProductsCategoriesRepository productsCategoriesRepository) {
        this.productsRepository = productsRepository;
        this.categoriesRepository = categoriesRepository;
        this.productsCategoriesRepository = productsCategoriesRepository;
    }

    @Override
    public Response addProducts(SelectedProductsDto selectedProductsDto) {
        CategoriesEntity categories = categoriesRepository.findByIdAndStatus(selectedProductsDto.getSelectedCategoryId(), 1);
        if (categories == null) {
            return ResponseBuilder.getFailResponse(HttpStatus.BAD_REQUEST, null, "Categories Id not found");
        }
        ProductsEntity products = productsRepository.findByNameAndStatus(selectedProductsDto.getName(), 1);
        if (products == null) {
            products = new ProductsEntity();
            products.setName(selectedProductsDto.getName());
            products.setDescription(selectedProductsDto.getDescription());
            products.setPrice(selectedProductsDto.getPrice());
            products.setStatus(1);
            products.setStock(selectedProductsDto.getStock());
            products.setImageUrl(selectedProductsDto.getImageUrl());
            ProductsEntity savedProducts = productsRepository.save(products);


            ProductCategoriesEntity productCategoriesEntity = new ProductCategoriesEntity();
            productCategoriesEntity.setProductId(savedProducts.getId());
            productCategoriesEntity.setCategoryId(categories.getId());
            productCategoriesEntity.setStatus(1);
            productsCategoriesRepository.save(productCategoriesEntity);
            return ResponseBuilder.getSuccessResponse(HttpStatus.CREATED, null,
                    "Successfully added products");

        }
        return ResponseBuilder.getFailResponse(HttpStatus.BAD_REQUEST, null, "Product already exists");
    }
}
