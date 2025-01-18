package com.goldonbuy.goldonbackend.catalogContext.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CartItemDTO {
    private Long itemId;
    private int quantity;
    private BigDecimal unitPrice;
    private ProductDTO product;
}
