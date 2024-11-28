package com.goldonbuy.goldonbackend.catalogContext.requestDTO;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class UpdateAddressRequest {
    private String street;
    private String city;
    private String zipCode;
    private String country;
}
