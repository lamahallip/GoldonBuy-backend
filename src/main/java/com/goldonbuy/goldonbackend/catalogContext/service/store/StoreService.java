package com.goldonbuy.goldonbackend.catalogContext.service.store;

import com.goldonbuy.goldonbackend.catalogContext.dto.ImageDTO;
import com.goldonbuy.goldonbackend.catalogContext.dto.StoreDTO;
import com.goldonbuy.goldonbackend.catalogContext.entity.Address;
import com.goldonbuy.goldonbackend.catalogContext.entity.Image;
import com.goldonbuy.goldonbackend.catalogContext.entity.Store;
import com.goldonbuy.goldonbackend.catalogContext.exceptions.ResourceNotFoundException;
import com.goldonbuy.goldonbackend.catalogContext.repository.AddressRepository;
import com.goldonbuy.goldonbackend.catalogContext.repository.ImageRepository;
import com.goldonbuy.goldonbackend.catalogContext.repository.StoreRepository;
import com.goldonbuy.goldonbackend.catalogContext.request.AddStoreRequest;
import com.goldonbuy.goldonbackend.catalogContext.request.UpdateStoreRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StoreService implements IStoreService {

    private final StoreRepository storeRepository;
    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;
    private final ImageRepository imageRepository;

    @Override
    public Store getStoreById(Long id) {
        return this.storeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("This store not found !"));
    }

    @Override
    public Store addStore(AddStoreRequest request) {
        Address address = Optional.ofNullable(this.addressRepository.findByStreet(request.getAddress().getStreet()))
                .orElseGet(() -> {
                    Address address1 = new Address();
                    address1.setCity(request.getAddress().getCity());
                    address1.setStreet(request.getAddress().getStreet());
                    address1.setCountry(request.getAddress().getCountry());
                    address1.setZipCode(request.getAddress().getZipCode());
                    return this.addressRepository.save(address1);
                });
        request.setAddress(address);
        return this.storeRepository.save(createStore(request, address));

    }
    private Store createStore(AddStoreRequest request, Address address) {
        return new Store(
                request.getName(),
                request.getContactName(),
                address
        );
    }

    @Override
    public void deleteStoreById(Long id) {
        this.storeRepository.findById(id)
                .ifPresentOrElse(storeRepository::delete, () -> {
                    throw new ResourceNotFoundException("This store not found !");
                });
    }

    @Override
    public Store updateStore(UpdateStoreRequest request, Long storeId) {
        return storeRepository.findById(storeId)
                .map(existingStore -> updateExistingStore(existingStore, request))
                .map(storeRepository::save)
                .orElseThrow(() -> new ResourceNotFoundException("This store not found"));
    }
    private Store updateExistingStore(Store existingStore, UpdateStoreRequest request){
        existingStore.setName(request.getName());
        existingStore.setContactName(request.getContactName());
        existingStore.setAddress(request.getAddress());

        return existingStore;
    }

    @Override
    public List<Store> getAllStore() {
        return storeRepository.findAll();
    }

    @Override
    public List<Store> getStoreByName(String name) {
        return this.storeRepository.findAllByName(name);
    }

    @Override
    public List<Store> getStoreByContactName(String contactName) {
        return this.storeRepository.findByContactName(contactName);
    }

    @Override
    public List<Store> getStoreByNameAndContactName(String name, String contactName) {
        return this.storeRepository.findByNameAndContactName(name, contactName);
    }

    @Override
    public List<Store> getStoreByType(String type) {
        return this.storeRepository.findByType(type);
    }

    @Override
    public List<Store> getStoreByAddressStreet(String street) {
        return this.storeRepository.findByAddressStreet(street);
    }

    @Override
    public List<Store> getStoreByAddressCity(String city) {
        return this.storeRepository.findByAddressCity(city);
    }

    @Override
    public List<Store> getStoreByAddressCountry(String country) {
        return this.storeRepository.findByAddressCountry(country);
    }

    @Override
    public List<Store> getStoreByTypeAndAddressCity(String type, String city) {
        return this.storeRepository.findByTypeAndAddressCity(type, city);
    }

    @Override
    public List<Store> getStoreByTypeAndAddressStreet(String type, String street) {
        return this.storeRepository.findByTypeAndAddressStreet(type, street);
    }

    @Override
    public List<Store> getStoreByTypeAndAddressStreetAndAddressCity(String type, String street, String city) {
        return this.storeRepository.findByTypeAndAddressCityAndAddressStreet(type, city, street);
    }

    @Override
    public Long countStoreByNameAndContactName(String name, String contactName) {
        return this.storeRepository.countByNameAndContactName(name, contactName);
    }

    @Override
    public StoreDTO convertToDTO(Store store) {
        StoreDTO storeDTO = modelMapper.map(store, StoreDTO.class);
        List<Image> images = imageRepository.findByStoreId(store.getId());
        List<ImageDTO> imageDTOs = images.stream()
                .map(image -> modelMapper.map(image, ImageDTO.class))
                .toList();
        storeDTO.setImages(imageDTOs);
        return storeDTO;
    }

    @Override
    public List<StoreDTO> getConvertedStores(List<Store> stores) {
        return stores.stream().map(this::convertToDTO).toList();
    }


}
