package com.nipa.agroneed.service;


import com.nipa.agroneed.dto.CategoriesDto;
import com.nipa.agroneed.dto.Response;

public interface CategoriesService {
    Response createCategory(CategoriesDto categoriesDto);
}
