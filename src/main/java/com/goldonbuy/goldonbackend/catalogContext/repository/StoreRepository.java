package com.goldonbuy.goldonbackend.catalogContext.repository;

import com.goldonbuy.goldonbackend.catalogContext.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {
    Optional<Store> findByName(String name);
    List<Store> findAllByName(String name);
    List<Store> findByContactName(String contactName);
    List<Store> findByNameAndContactName(String name, String contactName);
    List<Store> findByType(String type);
    List<Store> findByAddressStreet(String street);
    List<Store> findByAddressCountry(String country);
    List<Store> findByAddressCity(String city);
    List<Store> findByTypeAndAddressCity(String type, String city);
    List<Store> findByTypeAndAddressStreet(String type, String street);
    List<Store> findByTypeAndAddressCityAndAddressStreet(String type, String city, String street);
    Long countByNameAndContactName(String name, String contactName);
}
