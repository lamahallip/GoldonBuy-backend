package com.goldonbuy.goldonbackend.catalogContext.request;

import lombok.Data;

@Data
public class UpdateAddressRequest {
    private String street;
    private String city;
    private String zipCode;
    private String country;
}
