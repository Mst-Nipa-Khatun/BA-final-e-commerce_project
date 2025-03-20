package com.nipa.agroneed.dto;

public interface ProductsAndCategoryDetailsProjection {
    public String getCategoryName();
    public String getProductName();
    public String getProductDescription();
    public String getProductImageUrl();
    public Double getProductPrice();
    public Integer getStock();
    public String getStatus();
    public Long getProductId();

}
