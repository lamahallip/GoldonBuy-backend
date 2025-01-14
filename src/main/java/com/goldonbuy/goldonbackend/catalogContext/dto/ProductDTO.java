package com.goldonbuy.goldonbackend.catalogContext.dto;

import com.goldonbuy.goldonbackend.catalogContext.entity.Category;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private String brand;
    private Boolean genre;
    private String description;
    private BigDecimal price;
    private int inventory;
    private Category category;
    private StoreDTO store;
    private List<ImageDTO> images;
}
