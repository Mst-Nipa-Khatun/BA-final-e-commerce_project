package com.nipa.agroneed.repository;

import com.nipa.agroneed.dto.ShoppingCartProjection;
import com.nipa.agroneed.entity.ShoppingCartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCartEntity, Long> {
    ShoppingCartEntity findByUserIdAndProductIdAndStatus(Long userId, Long productId, Integer status);
    //ShoppingCartEntity findByProductIdAndStatus(Long productId, Integer status);

    @Query(value = "SELECT sc.id,p.name,sc.quantity,p.price,p.description,sc.product_id as productId\n" +
            "    from shopping_cart sc\n" +
            "    join products p where sc.product_id=p.id and sc.status=:status",nativeQuery = true)
    List<ShoppingCartProjection> findAllShoppingCart(@Param("status") Integer status);

}
