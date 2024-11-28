package com.goldonbuy.goldonbackend.catalogContext.service.store;

import com.goldonbuy.goldonbackend.catalogContext.entity.Address;
import com.goldonbuy.goldonbackend.catalogContext.entity.Store;
import com.goldonbuy.goldonbackend.catalogContext.exceptions.RessourceNotFoundException;
import com.goldonbuy.goldonbackend.catalogContext.repository.AddressRepository;
import com.goldonbuy.goldonbackend.catalogContext.repository.StoreRepository;
import com.goldonbuy.goldonbackend.catalogContext.requestDTO.AddStoreRequest;
import com.goldonbuy.goldonbackend.catalogContext.requestDTO.UpdateStoreRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StoreService implements IStoreService {

    private final StoreRepository storeRepository;
    private final AddressRepository addressRepository;

    @Override
    public Store getStoreById(Long id) {
        return this.storeRepository.findById(id)
                .orElseThrow(() -> new RessourceNotFoundException("This store not found !"));
    }

    @Override
    public Store addStore(AddStoreRequest request) {
        Address address = Optional.ofNullable(this.addressRepository.findByCity(request.getAddress().getCity()))
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
                request.getType(),
                address
        );
    }

    @Override
    public void deleteStoreById(Long id) {
        this.storeRepository.findById(id)
                .ifPresentOrElse(storeRepository::delete, () -> {
                    throw new RessourceNotFoundException("This store not found !");
                });
    }

    @Override
    public Store updateStore(UpdateStoreRequest request, Long storeId) {
        return storeRepository.findById(storeId)
                .map(existingStore -> updateExistingStore(existingStore, request))
                .map(storeRepository::save)
                .orElseThrow(() -> new RessourceNotFoundException("This store not found"));
    }
    private Store updateExistingStore(Store existingStore, UpdateStoreRequest request){
        existingStore.setName(request.getName());
        existingStore.setContactName(request.getContactName());
        existingStore.setAddress(request.getAddress());
        existingStore.setType(request.getType());

        return existingStore;
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


}
