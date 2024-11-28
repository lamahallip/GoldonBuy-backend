package com.goldonbuy.goldonbackend.catalogContext.service.address;

import com.goldonbuy.goldonbackend.catalogContext.entity.Address;
import com.goldonbuy.goldonbackend.catalogContext.requestDTO.AddAddressRequest;
import com.goldonbuy.goldonbackend.catalogContext.requestDTO.UpdateAddressRequest;

public interface IAddressService {
    Address addAddress(AddAddressRequest request);
    Address updateAddress(UpdateAddressRequest request, Long addressId);
}
