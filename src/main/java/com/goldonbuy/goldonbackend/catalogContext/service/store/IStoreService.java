package com.goldonbuy.goldonbackend.catalogContext.service.store;

import com.goldonbuy.goldonbackend.catalogContext.dto.StoreDTO;
import com.goldonbuy.goldonbackend.catalogContext.entity.Store;
import com.goldonbuy.goldonbackend.catalogContext.request.AddStoreRequest;
import com.goldonbuy.goldonbackend.catalogContext.request.UpdateStoreRequest;

import java.util.List;

public interface IStoreService {
    Store getStoreById(Long id);
    Store addStore(AddStoreRequest request);
    void deleteStoreById(Long id);
    Store updateStore(UpdateStoreRequest request, Long storeId);
    List<Store> getAllStore();
    List<Store> getStoreByName(String name);
    List<Store> getStoreByContactName(String contactName);
    List<Store> getStoreByNameAndContactName(String name, String contactName);
    List<Store> getStoreByType(String type);
    List<Store> getStoreByAddressCity(String city);
    List<Store> getStoreByAddressStreet(String street);
    List<Store> getStoreByAddressCountry(String country);
    List<Store> getStoreByTypeAndAddressCity(String type, String city);
    List<Store> getStoreByTypeAndAddressStreet(String type, String street);
    List<Store> getStoreByTypeAndAddressStreetAndAddressCity(String type, String street, String city);
    Long countStoreByNameAndContactName(String name, String contactName);


    StoreDTO convertToDTO(Store store);

    List<StoreDTO> getConvertedStores(List<Store> stores);
}
