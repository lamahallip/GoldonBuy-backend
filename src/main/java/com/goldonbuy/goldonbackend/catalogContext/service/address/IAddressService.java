package com.goldonbuy.goldonbackend.catalogContext.service.address;

import com.goldonbuy.goldonbackend.catalogContext.dto.AddressDTO;
import com.goldonbuy.goldonbackend.catalogContext.entity.Address;
import com.goldonbuy.goldonbackend.catalogContext.request.AddAddressRequest;
import com.goldonbuy.goldonbackend.catalogContext.request.UpdateAddressRequest;

public interface IAddressService {
    Address addAddress(AddAddressRequest request);
    Address updateAddress(UpdateAddressRequest request, Long addressId);

    AddressDTO convertToDTO(Address address);
}
