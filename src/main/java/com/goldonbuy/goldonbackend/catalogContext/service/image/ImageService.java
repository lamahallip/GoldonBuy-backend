package com.goldonbuy.goldonbackend.catalogContext.service.image;

import com.goldonbuy.goldonbackend.catalogContext.entity.Product;
import com.goldonbuy.goldonbackend.catalogContext.entity.Store;
import com.goldonbuy.goldonbackend.catalogContext.exceptions.ResourceNotFoundException;
import com.goldonbuy.goldonbackend.catalogContext.repository.ImageRepository;
import com.goldonbuy.goldonbackend.catalogContext.request.AddImageRequest;
import com.goldonbuy.goldonbackend.catalogContext.service.product.ProductService;
import com.goldonbuy.goldonbackend.catalogContext.service.store.StoreService;
import com.goldonbuy.goldonbackend.catalogContext.entity.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;



import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {

    private final ImageRepository imageRepository;
    private final ProductService productService;
    private final StoreService storeService;


    /**
     *  Method to get image by ID from Database
     * ***/
    @Override
    public com.goldonbuy.goldonbackend.catalogContext.entity.Image getImageById(Long id) {
        return this.imageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No image found with id "+ id));
    }

    /**
     *  Method to delete image by ID from Database
     * ***/
    @Override
    public void deleteImageById(Long id) {
        this.imageRepository.findById(id)
                .ifPresentOrElse(imageRepository::delete,
                        () -> {
                            throw new ResourceNotFoundException("No image found with id " + id);
                        });
    }

    /**
     *  Method to save product images from Database
     * ***/
    @Override
    public java.util.List<AddImageRequest> saveImagesProduct(java.util.List<MultipartFile> files, Long productId) {

        Product product = this.productService.getProductById(productId);
        java.util.List<AddImageRequest> savedAddImageRequests = new ArrayList<>();

       for (MultipartFile file : files) {

           try {
               com.goldonbuy.goldonbackend.catalogContext.entity.Image image = new com.goldonbuy.goldonbackend.catalogContext.entity.Image();
               image.setFileName(file.getOriginalFilename());
               image.setFileType(file.getContentType());
               image.setImage(new SerialBlob(file.getBytes()));
               image.setProduct(product);

               String buildDownloadUrl = "/api/v1/images/image/download/";

               String downloadUrl = buildDownloadUrl + image.getId();
               image.setDownloadUrl(downloadUrl);
               com.goldonbuy.goldonbackend.catalogContext.entity.Image savedImage = imageRepository.save(image);
               savedImage.setDownloadUrl(buildDownloadUrl + savedImage.getId());
               imageRepository.save(savedImage);

               AddImageRequest addImageRequest = new AddImageRequest();
               addImageRequest.setFileName(savedImage.getFileName());
               addImageRequest.setId(savedImage.getId());
               addImageRequest.setDownloadUrl(savedImage.getDownloadUrl());
               savedAddImageRequests.add(addImageRequest);

           } catch (IOException | SQLException e) {
                throw new RuntimeException(e.getMessage());
           }
       }

        return savedAddImageRequests;
    }

    /**
     *  Method to save store images from Database
     * ***/
    @Override
    public java.util.List<AddImageRequest> saveImageStore(java.util.List<MultipartFile> files, Long storeId) {
        Store store = this.storeService.getStoreById(storeId) ;
        java.util.List<AddImageRequest> savedAddImageRequests = new ArrayList<>();

        for (MultipartFile file : files) {

            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setStore(store);

                String buildDownloadUrl = "/api/v1/images/image/download/";

                String downloadUrl = buildDownloadUrl+ image.getId();
                image.setDownloadUrl(downloadUrl);
                com.goldonbuy.goldonbackend.catalogContext.entity.Image savedImage = imageRepository.save(image);
                savedImage.setDownloadUrl(buildDownloadUrl+savedImage.getId());
                imageRepository.save(savedImage);

                AddImageRequest addImageRequest = new AddImageRequest();
                addImageRequest.setFileName(savedImage.getFileName());
                addImageRequest.setId(savedImage.getId());
                addImageRequest.setDownloadUrl(savedImage.getDownloadUrl());
                savedAddImageRequests.add(addImageRequest);

            } catch (IOException | SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        return savedAddImageRequests;
    }


    /**
     *  Method to update old Image from Database
     * ***/
    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image image = getImageById(imageId);

        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
