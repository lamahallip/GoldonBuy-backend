package com.goldonbuy.goldonbackend.catalogContext.service.address;

import com.goldonbuy.goldonbackend.catalogContext.dto.AddressDTO;
import com.goldonbuy.goldonbackend.catalogContext.entity.Address;
import com.goldonbuy.goldonbackend.catalogContext.exceptions.ResourceNotFoundException;
import com.goldonbuy.goldonbackend.catalogContext.repository.AddressRepository;
import com.goldonbuy.goldonbackend.catalogContext.request.AddAddressRequest;
import com.goldonbuy.goldonbackend.catalogContext.request.UpdateAddressRequest;
import com.goldonbuy.goldonbackend.catalogContext.service.store.IStoreService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService implements IAddressService {

    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;
    private final IStoreService storeService;

    @Override
    public Address addAddress(AddAddressRequest request) {
        return this.addressRepository.save(createAddress(request));
    }
    private Address createAddress(AddAddressRequest request) {
        Address address = new Address();
        address.setStreet(request.getStreet());
        address.setCountry(request.getCountry());
        address.setZipCode(request.getZipCode());
        address.setCity(request.getCity());
        return address;
    }


    @Override
    public Address updateAddress(UpdateAddressRequest request, Long addressId) {
        return this.addressRepository.findById(addressId)
                .map(existingAddress -> updateExistingAddress(existingAddress, request))
                .map(addressRepository::save)
                .orElseThrow(() -> new ResourceNotFoundException("This address not found"));
    }
    private Address updateExistingAddress(Address existingAddress, UpdateAddressRequest request) {
        existingAddress.setCity(request.getCity());
        existingAddress.setStreet(request.getStreet());
        existingAddress.setCountry(request.getCountry());
        existingAddress.setZipCode(request.getZipCode());

        return existingAddress;
    }

    @Override
    public AddressDTO convertToDTO(Address address) {
        AddressDTO addressDTO = modelMapper.map(address, AddressDTO.class);
        //List<StoreDTO> storeDTOs = storeService.getConvertedStores(address.getStores());
        //addressDTO.setStores(storeDTOs);
        return addressDTO;
    }

}
