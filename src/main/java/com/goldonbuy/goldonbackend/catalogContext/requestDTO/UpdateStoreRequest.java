package com.goldonbuy.goldonbackend.catalogContext.requestDTO;

import com.goldonbuy.goldonbackend.catalogContext.entity.Address;
import com.goldonbuy.goldonbackend.catalogContext.entity.TypeStore;
import lombok.Data;

@Data
public class UpdateStoreRequest {
    private String name;
    private String contactName;
    private TypeStore type;
    private Address address;
}
