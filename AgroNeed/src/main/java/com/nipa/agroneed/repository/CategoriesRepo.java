package com.nipa.agroneed.repository;


import com.nipa.agroneed.entity.CategoriesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriesRepo extends JpaRepository<CategoriesEntity,Long> {

    CategoriesEntity findByNameAndStatus(String name,Integer status);
    CategoriesEntity findByIdAndStatus(Long id,Integer status);

   // CategoriesEntity findByParentIdAndStatus(Integer parentId, Integer status);
}
