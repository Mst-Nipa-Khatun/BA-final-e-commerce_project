package com.nipa.agroneed.dto;

import lombok.Data;

@Data
public class SelectedProductsDto {
    private Long selectedCategoryId;
    private String name;
    private String description;
    private Double price;
    private Integer stock;
}
