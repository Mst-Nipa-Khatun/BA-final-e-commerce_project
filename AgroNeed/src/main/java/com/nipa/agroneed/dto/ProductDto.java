package com.nipa.agroneed.dto;

import lombok.Data;

@Data
public class ProductDto extends BaseDto {
    private String name;
    private String description;
    private Double price;
    private Integer stock;
    private String imageUrl;
}
