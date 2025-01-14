package com.goldonbuy.goldonbackend.catalogContext.dto;

import com.goldonbuy.goldonbackend.catalogContext.entity.*;
import lombok.Data;

import java.util.List;

@Data
public class StoreDTO {
    private Long id;
    private String name;
    private String contactName;
    private TypeStore type;
    private SizeStore size;
    private List<ImageDTO> images;
    //private List<ProductDTO> products;
    private AddressDTO address;
}
