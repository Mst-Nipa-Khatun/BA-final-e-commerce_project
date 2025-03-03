package com.nipa.agroneed.controller;


import com.nipa.agroneed.dto.CategoriesDto;
import com.nipa.agroneed.dto.Response;
import com.nipa.agroneed.service.CategoriesService;
import com.nipa.agroneed.utils.UrlConstraint;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(UrlConstraint.AgroNeed.ROOT)
public class CategoriesController {

    private final CategoriesService categoriesService;

    public CategoriesController(CategoriesService categoriesService) {
        this.categoriesService = categoriesService;
    }
    @PostMapping(UrlConstraint.AgroNeed.CREATE)
    public Response createCategory(@RequestBody CategoriesDto categoriesDto) {
        return categoriesService.createCategory(categoriesDto);
    }
}
