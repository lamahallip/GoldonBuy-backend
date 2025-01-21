package com.goldonbuy.goldonbackend.catalogContext.service.product;

import com.goldonbuy.goldonbackend.catalogContext.dto.ImageDTO;
import com.goldonbuy.goldonbackend.catalogContext.dto.ProductDTO;
import com.goldonbuy.goldonbackend.catalogContext.dto.StoreDTO;
import com.goldonbuy.goldonbackend.catalogContext.entity.Category;
import com.goldonbuy.goldonbackend.catalogContext.entity.Image;
import com.goldonbuy.goldonbackend.catalogContext.entity.Product;
import com.goldonbuy.goldonbackend.catalogContext.entity.Store;
import com.goldonbuy.goldonbackend.catalogContext.exceptions.AlreadyExistingException;
import com.goldonbuy.goldonbackend.catalogContext.exceptions.ProductNotFoundException;
import com.goldonbuy.goldonbackend.catalogContext.exceptions.StoreNotFoundException;
import com.goldonbuy.goldonbackend.catalogContext.repository.CategoryRepository;
import com.goldonbuy.goldonbackend.catalogContext.repository.ImageRepository;
import com.goldonbuy.goldonbackend.catalogContext.repository.ProductRepository;
import com.goldonbuy.goldonbackend.catalogContext.repository.StoreRepository;
import com.goldonbuy.goldonbackend.catalogContext.request.AddProductRequest;
import com.goldonbuy.goldonbackend.catalogContext.request.UpdateProductRequest;
import com.goldonbuy.goldonbackend.catalogContext.service.store.IStoreService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final StoreRepository storeRepository;
    private final ModelMapper modelMapper;
    private final ImageRepository imageRepository;
    private final IStoreService storeService;

    /**
     *  Method to add new product in Database
     * ***/
    @Override
    public Product addProduct(AddProductRequest request) {

        if(productExist(request.getBrand(), request.getName())) {
            throw new AlreadyExistingException(request.getBrand()+" "+request.getName()+" already exists ! You may update this product instead !");
        }

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
    private Boolean productExist(String brand, String name) {
        return this.productRepository.existsByBrandAndName(brand, name);
    }

    private Product createProduct(AddProductRequest request, Category category, Store store) {
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getGenre(),
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

    @Override
    public List<ProductDTO> getConvertedProducts(List<Product> products) {
        return products.stream().map(this::convertToDTO).toList();
    }

    @Override
    public ProductDTO convertToDTO(Product product) {
        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
        List<Image> images = imageRepository.findByProductId(product.getId());
        List<ImageDTO> imageDTOs = images.stream()
                .map(image -> modelMapper.map(image, ImageDTO.class))
                .toList();
        StoreDTO storeDTO = storeService.convertToDTO(product.getStore());
        productDTO.setImages(imageDTOs);
        productDTO.setStore(storeDTO);
        return productDTO;
    }
}
