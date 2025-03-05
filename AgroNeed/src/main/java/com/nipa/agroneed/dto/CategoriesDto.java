package com.nipa.agroneed.dto;

import lombok.Data;

@Data
public class CategoriesDto extends BaseDto {
    private String name;
    private Long parentId;

}
