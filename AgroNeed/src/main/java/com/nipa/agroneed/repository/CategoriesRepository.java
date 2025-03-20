package com.nipa.agroneed.repository;


import com.nipa.agroneed.entity.CategoriesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoriesRepository extends JpaRepository<CategoriesEntity,Long> {

    CategoriesEntity findByNameAndStatus(String name,Integer status);
    CategoriesEntity findByIdAndStatus(Long id,Integer status);
    List<CategoriesEntity> findAllByStatus(Integer status);

    @Query(value = "Select *from categories where parent_id is null",nativeQuery = true)
    List<CategoriesEntity> findAllParentCategories();


    List<CategoriesEntity> findByParentIdAndStatus(Long parentId, Integer status);
}
