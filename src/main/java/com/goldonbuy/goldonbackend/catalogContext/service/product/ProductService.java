package com.goldonbuy.goldonbackend.catalogContext.service.product;

import com.goldonbuy.goldonbackend.catalogContext.entity.Category;
import com.goldonbuy.goldonbackend.catalogContext.entity.Product;
import com.goldonbuy.goldonbackend.catalogContext.entity.Store;
import com.goldonbuy.goldonbackend.catalogContext.exceptions.ProductNotFoundException;
import com.goldonbuy.goldonbackend.catalogContext.exceptions.StoreNotFoundException;
import com.goldonbuy.goldonbackend.catalogContext.repository.CategoryRepository;
import com.goldonbuy.goldonbackend.catalogContext.repository.ProductRepository;
import com.goldonbuy.goldonbackend.catalogContext.repository.StoreRepository;
import com.goldonbuy.goldonbackend.catalogContext.requestDTO.AddProductRequest;
import com.goldonbuy.goldonbackend.catalogContext.requestDTO.UpdateProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductServcie {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private StoreRepository storeRepository;

    /**
     *  Method to add new product in Database
     * ***/
    @Override
    public Product addProduct(AddProductRequest request) {

        Category category = Optional.ofNullable(this.categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(() -> {
                    Category newCategory = new Category();
                    newCategory.setName(request.getCategory().getName());
                    return this.categoryRepository.save(newCategory);
                        });
            request.setCategory(category);

        Store store = this.storeRepository.findByName(request.getStore().getName())
                .orElseThrow(() -> new StoreNotFoundException("This store not found !"));
            request.setStore(store);

        return this.productRepository.save(createProduct(request, category, store));
    }
    private Product createProduct(AddProductRequest request, Category category, Store store) {
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getDescription(),
                request.getPrice(),
                request.getInventory(),
                category,
                store
        );
    }

    /**
     *  Method to get product by id form Database
     * ***/
    @Override
    public Product getProductById(Long id) {
        return this.productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("This product not found"));
    }


    /**
     *  Method to delete product by id form Database
     * ***/
    @Override
    public void deleteProductById(Long id) {
        this.productRepository.findById(id)
                .ifPresentOrElse(productRepository::delete,
                        () -> { throw new ProductNotFoundException("This product not found");});

    }

    /**
     *  Method to update product by product and productId form Database
     * ***/
    @Override
    public Product updateProduct(UpdateProductRequest request, Long productId) {
        return productRepository.findById(productId)
                .map(existingProduct -> updateExistingProduct(existingProduct, request))
                .map(this.productRepository::save)
                .orElseThrow(() -> new ProductNotFoundException("This product not found"));
    }
    private Product updateExistingProduct(Product existingProduct, UpdateProductRequest request){

        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setStore(request.getStore());
        existingProduct.setDescription(request.getDescription());
        Category category = this.categoryRepository.findByName(request.getCategory().getName());
        existingProduct.setCategory(category);

        return existingProduct;
    }

    /**
     *  Method to get all products form Database
     * ***/
    @Override
    public List<Product> getAllProducts() {
        return this.productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return this.productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return this.productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return this.productRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return this.productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return this.productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public List<Product> getProductsByBrancAndName(String brand, String name) {
        return this.productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public List<Product> getProductsByStore(String store) {
        return this.productRepository.findByStoreName(store);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return this.productRepository.countByBrandAndName(brand, name);
    }
}
