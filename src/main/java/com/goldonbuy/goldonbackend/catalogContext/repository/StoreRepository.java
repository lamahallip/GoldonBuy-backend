package com.goldonbuy.goldonbackend.catalogContext.repository;

import com.goldonbuy.goldonbackend.catalogContext.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {
    Optional<Store> findByName(String name);
}
