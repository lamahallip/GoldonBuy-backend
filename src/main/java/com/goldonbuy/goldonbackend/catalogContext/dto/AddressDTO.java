package com.goldonbuy.goldonbackend.catalogContext.dto;

import lombok.Data;

import java.util.List;

@Data
public class AddressDTO {
    private Long id;
    private String street;
    private String city;
    private String zipCode;
    private String country;
    //private List<StoreDTO> stores;
}
