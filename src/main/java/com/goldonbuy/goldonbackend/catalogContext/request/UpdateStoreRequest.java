package com.goldonbuy.goldonbackend.catalogContext.request;

import com.goldonbuy.goldonbackend.catalogContext.entity.Address;
import com.goldonbuy.goldonbackend.catalogContext.entity.TypeStore;
import lombok.Data;

@Data
public class UpdateStoreRequest {
    private String name;
    private String contactName;
    private Address address;
}
