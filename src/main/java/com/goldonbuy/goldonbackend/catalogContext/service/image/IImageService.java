package com.goldonbuy.goldonbackend.catalogContext.service.image;

import com.goldonbuy.goldonbackend.catalogContext.request.AddImageRequest;
import org.springframework.web.multipart.MultipartFile;

public interface IImageService {
    com.goldonbuy.goldonbackend.catalogContext.entity.Image getImageById(Long id);
    void deleteImageById(Long id);
    java.util.List<AddImageRequest> saveImagesProduct(java.util.List<MultipartFile> files, Long productId);
    java.util.List<AddImageRequest> saveImageStore(java.util.List<MultipartFile> files, Long storeId);
    void updateImage(MultipartFile file, Long imageId);
}
