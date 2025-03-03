package com.nipa.agroneed.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Orders")
public class OrdersEntity extends BaseEntity {
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "total_price")
    private Double totalPrice;

    //status in enum

//    order_id INT PRIMARY KEY AUTO_INCREMENT,
//    user_id INT,
//    total_price DECIMAL(10,2),
//    status ENUM('pending', 'shipped', 'delivered', 'cancelled') DEFAULT 'pending',
//    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
//    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE
}
