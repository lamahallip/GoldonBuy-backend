package com.goldonbuy.goldonbackend.catalogContext.request;

import com.goldonbuy.goldonbackend.catalogContext.entity.Address;
import lombok.Data;

@Data
public class AddStoreRequest {
    private String name;
    private String contactName;
    private Address address;
}
