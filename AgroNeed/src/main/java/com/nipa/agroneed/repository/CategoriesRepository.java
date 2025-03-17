package com.nipa.agroneed.repository;


import com.nipa.agroneed.entity.CategoriesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriesRepository extends JpaRepository<CategoriesEntity,Long> {

    CategoriesEntity findByNameAndStatus(String name,Integer status);
    CategoriesEntity findByIdAndStatus(Long id,Integer status);
    List<CategoriesEntity> findAllByStatus(Integer status);


    CategoriesEntity findByParentIdAndStatus(Long parentId, Integer status);
}
