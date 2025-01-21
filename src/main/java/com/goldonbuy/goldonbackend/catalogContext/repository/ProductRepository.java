package com.goldonbuy.goldonbackend.catalogContext.repository;

import com.goldonbuy.goldonbackend.catalogContext.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategoryName(String category);

    List<Product> findByBrand(String brand);

    List<Product> findByName(String name);

    List<Product> findByCategoryNameAndBrand(String category, String brand);

    List<Product> findByStoreName(String store);

    Long countByBrandAndName(String brand, String name);

    List<Product> findByBrandAndName(String brand, String name);

    Boolean existsByBrandAndName(String brand, String name);
}
