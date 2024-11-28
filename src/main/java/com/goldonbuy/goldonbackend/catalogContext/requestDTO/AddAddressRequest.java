package com.goldonbuy.goldonbackend.catalogContext.requestDTO;

import com.goldonbuy.goldonbackend.catalogContext.entity.Address;
import lombok.Data;

@Data
public class AddAddressRequest {
    private String street;
    private String city;
    private String zipCode;
    private String country;
}
