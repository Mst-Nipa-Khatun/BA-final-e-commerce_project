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

import java.util.ArrayList;
import java.util.List;

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

        CategoriesEntity category = new CategoriesEntity();
        category.setName(categoriesDto.getName());
        category.setParentId(categoriesDto.getParentId());
        category.setStatus(1);

        if (categoriesDto.getParentId() != null) {
            CategoriesEntity parentCategory = categoriesRepo.findByIdAndStatus(categoriesDto.getParentId(), 1);
            if (parentCategory == null) {
                return ResponseBuilder.getFailResponse(HttpStatus.BAD_REQUEST, null, "ParentId Not Found");
            }
            category.setParentId(parentCategory.getId());
        }
        CategoriesEntity savedCategory = categoriesRepo.save(category);

        return ResponseBuilder.getSuccessResponse(HttpStatus.CREATED, savedCategory, "Category created successfully");
    }

    @Override
    public Response getAllCategories() {
        List<CategoriesEntity> categoriesEntities = categoriesRepo.findAllByStatus(1);
        if (!categoriesEntities.isEmpty()) {

            List<CategoriesDto> categoriesDtos = new ArrayList<>();

            for (CategoriesEntity categoriesEntity : categoriesEntities) {
                CategoriesDto categoriesDto = new CategoriesDto();
                categoriesDto.setId(categoriesEntity.getId());
                categoriesDto.setName(categoriesEntity.getName());
                categoriesDto.setParentId(categoriesEntity.getParentId());
                categoriesDto.setStatus(categoriesEntity.getStatus());
                categoriesDtos.add(categoriesDto);
            }
            return ResponseBuilder.getSuccessResponse(HttpStatus.OK, categoriesDtos, "Categories listed");
        }
        return ResponseBuilder.getFailResponse(HttpStatus.BAD_REQUEST, null, "No categories found");
    }

    @Override
    public Response getCategoryById(Long id) {
        CategoriesEntity categories = categoriesRepo.findByIdAndStatus(id, 1);
        if (categories != null) {
            CategoriesDto categoriesDto = new CategoriesDto();
            categoriesDto.setName(categories.getName());
            categoriesDto.setParentId(categories.getParentId());
            categoriesDto.setStatus(categories.getStatus());
            return ResponseBuilder.getSuccessResponse(HttpStatus.OK, categoriesDto, "Category found");
        }
        return ResponseBuilder.getFailResponse(HttpStatus.BAD_REQUEST, null, "No category found");
    }

    @Override
    public Response getCategoryByParentId(Long parentId) {
        CategoriesEntity categories = categoriesRepo.findByParentIdAndStatus(parentId, 1);
        if (categories != null) {
            CategoriesDto categoriesDto = new CategoriesDto();
            categoriesDto.setName(categories.getName());
            categoriesDto.setParentId(categories.getParentId());
            categoriesDto.setStatus(categories.getStatus());
            return ResponseBuilder.getSuccessResponse(HttpStatus.OK, categoriesDto, "Category found");
        }
        return ResponseBuilder.getFailResponse(HttpStatus.BAD_REQUEST, null, "No category found");
    }

    @Override
    public Response deleteCategoryById(Long id) {
        CategoriesEntity categories = categoriesRepo.findByIdAndStatus(id, 1);
        if (categories != null) {
            categories.setStatus(0);
            CategoriesEntity deletedCategory = categoriesRepo.save(categories);

            CategoriesDto categoriesDto = new CategoriesDto();
            categoriesDto.setName(deletedCategory.getName());
            categoriesDto.setParentId(deletedCategory.getParentId());
            return ResponseBuilder.getSuccessResponse(HttpStatus.OK, deletedCategory, "Category deleted successfully");

        }
        return ResponseBuilder.getFailResponse(HttpStatus.BAD_REQUEST, null, "No category found");
    }

    @Override
    public Response editCategoryById(Long id, CategoriesDto categoriesDto) {
        CategoriesEntity categories = categoriesRepo.findByIdAndStatus(id, 1);
        if (categories != null) {
            categories.setName(categoriesDto.getName());
            categories.setParentId(categoriesDto.getParentId());
            categories.setStatus(categoriesDto.getStatus());
            CategoriesEntity savedCategory = categoriesRepo.save(categories);

            CategoriesDto updatedCategoriesDto = new CategoriesDto();
            updatedCategoriesDto.setId(savedCategory.getId());
            updatedCategoriesDto.setName(savedCategory.getName());
            updatedCategoriesDto.setParentId(savedCategory.getParentId());
            updatedCategoriesDto.setStatus(savedCategory.getStatus());


            return ResponseBuilder.getSuccessResponse(HttpStatus.OK, updatedCategoriesDto, "Category edited successfully");
        }
        return ResponseBuilder.getFailResponse(HttpStatus.BAD_REQUEST, null, "No category found");
    }
}