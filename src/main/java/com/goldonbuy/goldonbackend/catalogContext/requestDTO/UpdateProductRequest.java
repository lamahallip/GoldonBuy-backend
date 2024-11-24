package com.goldonbuy.goldonbackend.catalogContext.requestDTO;

import com.goldonbuy.goldonbackend.catalogContext.entity.Category;
import com.goldonbuy.goldonbackend.catalogContext.entity.Store;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateProductRequest {
    private String name;
    private String brand;
    private String description;
    private BigDecimal price;
    private int inventory;
    private Category category;
    private Store store;
}
