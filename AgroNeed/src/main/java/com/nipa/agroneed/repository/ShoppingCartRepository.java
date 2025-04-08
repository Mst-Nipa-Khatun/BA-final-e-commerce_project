package com.nipa.agroneed.repository;

import com.nipa.agroneed.entity.ShoppingCartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCartEntity, Long> {
    ShoppingCartEntity findByUserIdAndProductIdAndStatus(Long userId, Long productId, Integer status);
    //ShoppingCartEntity findByProductIdAndStatus(Long productId, Integer status);
}
