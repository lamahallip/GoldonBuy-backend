package com.goldonbuy.goldonbackend.catalogContext.service.product;

import com.goldonbuy.goldonbackend.catalogContext.entity.Product;
import com.goldonbuy.goldonbackend.catalogContext.requestDTO.AddProductRequest;
import com.goldonbuy.goldonbackend.catalogContext.requestDTO.UpdateProductRequest;

import java.util.List;

public interface IProductServcie {


    Product addProduct(AddProductRequest request);
    Product getProductById(Long id);
    void deleteProductById(Long id);
    Product updateProduct(UpdateProductRequest request, Long productId);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String brand, String name);
    List<Product> getProductsByBrancAndName(String brand, String name);
    List<Product> getProductsByStore(String store);


    Long countProductsByBrandAndName(String brand, String name);

}
