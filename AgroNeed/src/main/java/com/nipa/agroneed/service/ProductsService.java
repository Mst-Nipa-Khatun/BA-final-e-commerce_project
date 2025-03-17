package com.nipa.agroneed.service;

import com.nipa.agroneed.dto.Response;
import com.nipa.agroneed.dto.SelectedProductsDto;

public interface ProductsService {
    Response addProducts(SelectedProductsDto selectedProductsDto);
}
