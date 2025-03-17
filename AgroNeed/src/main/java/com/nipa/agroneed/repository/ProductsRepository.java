package com.nipa.agroneed.repository;

import com.nipa.agroneed.entity.ProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepository extends JpaRepository<ProductsEntity,Long> {
    ProductsEntity findByNameAndStatus(String name, Integer status);
}
