package com.nipa.agroneed.controller;


import com.nipa.agroneed.dto.CategoriesDto;
import com.nipa.agroneed.dto.Response;
import com.nipa.agroneed.service.CategoriesService;
import com.nipa.agroneed.utils.UrlConstraint;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping(UrlConstraint.AgroNeed.GET_ALL)
    public Response getAllCategories() {
        return categoriesService.getAllCategories();
    }
    @GetMapping(UrlConstraint.AgroNeed.GET_BY_ID)
    public Response getCategoryById(@PathVariable("id") Long id) {
        return categoriesService.getCategoryById(id);
    }
    @GetMapping(UrlConstraint.AgroNeed.GET_BY_ParentId)
    public Response getCategoryByParentId(@PathVariable("parentId") Long parentId) {
        return categoriesService.getCategoryByParentId(parentId);
    }
    @DeleteMapping(UrlConstraint.AgroNeed.DELETE_BY_ID)
    public Response deleteCategoryById(@PathVariable("id") Long id) {
        return categoriesService.deleteCategoryById(id);
    }
    @PutMapping(UrlConstraint.AgroNeed.EDIT_BY_ID)
    public Response editCategoryById(@PathVariable("id") Long id, @RequestBody CategoriesDto categoriesDto) {
        return categoriesService.editCategoryById(id,categoriesDto);
    }
}
