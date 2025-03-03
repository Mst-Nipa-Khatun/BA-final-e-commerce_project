package com.nipa.agroneed.service.Impl;

import com.nipa.agroneed.dto.CategoriesDto;
import com.nipa.agroneed.dto.Response;
import com.nipa.agroneed.entity.CategoriesEntity;
import com.nipa.agroneed.repository.CategoriesRepo;
import com.nipa.agroneed.service.CategoriesService;
import com.nipa.agroneed.utils.ResponseBuilder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Data
@Service
public class CategoriesServiceImpl implements CategoriesService {

    private final CategoriesRepo categoriesRepo;

    public CategoriesServiceImpl(CategoriesRepo categoriesRepo) {
        this.categoriesRepo = categoriesRepo;
    }

    @Override
    public Response createCategory(CategoriesDto categoriesDto) {
        CategoriesEntity categories = categoriesRepo.findByNameAndStatus(categoriesDto.getName(), 1);
        if (categories != null) {
            return ResponseBuilder.getFailResponse(HttpStatus.BAD_REQUEST, null, "Category already exists");
        }
        if (categories.getParentId() == null) {
            CategoriesEntity category = new CategoriesEntity();
            category.setName(categoriesDto.getName());
            category.setParentId(categoriesDto.getParentId());
            category.setStatus(1);
            CategoriesEntity saved = categoriesRepo.save(category);
            return ResponseBuilder.getSuccessResponse(HttpStatus.CREATED, saved, "Category created successfully");
        }

        CategoriesEntity parentId = categoriesRepo.findByIdAndStatus(categories.getId(), 1);
        if (parentId == null) {
            return ResponseBuilder.getFailResponse(HttpStatus.BAD_REQUEST, null, "ParentId Not Found");
        }
        CategoriesEntity category = new CategoriesEntity();
        category.setName(categoriesDto.getName());
        category.setParentId(categoriesDto.getParentId());
        category.setStatus(1);
        CategoriesEntity saved = categoriesRepo.save(category);

        return ResponseBuilder.getSuccessResponse(HttpStatus.CREATED, null, "success");
    }
}
