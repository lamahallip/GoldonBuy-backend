package com.goldonbuy.goldonbackend.catalogContext.controller;

import com.goldonbuy.goldonbackend.catalogContext.entity.Image;
import com.goldonbuy.goldonbackend.catalogContext.exceptions.RessourceNotFoundException;
import com.goldonbuy.goldonbackend.catalogContext.requestDTO.AddImageRequest;
import com.goldonbuy.goldonbackend.catalogContext.response.ApiResponse;
import com.goldonbuy.goldonbackend.catalogContext.service.image.IImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix/images}")
public class ImageController {

    private final IImageService imageService;

    @PostMapping("/upload-images-product")
    public ResponseEntity<ApiResponse> saveImagesProduct(@RequestParam List<MultipartFile> files, @RequestParam Long productId) {

        try {
            List<AddImageRequest> addImageRequests = imageService.saveImagesProduct(files, productId);
            return ResponseEntity.ok(new ApiResponse("Upload success !", addImageRequests));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Upload failed !", e.getMessage()));
        }
    }

    @PostMapping("/upload-images-store")
    public ResponseEntity<ApiResponse> saveImagesStore(@RequestParam List<MultipartFile> files, @RequestParam Long storeId) {

        try {
            List<AddImageRequest> addImageRequests = imageService.saveImageStore(files, storeId);
            return  ResponseEntity.ok(new ApiResponse("Upload success !", addImageRequests));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Upload failed", e.getMessage()));
        }
    }

    @GetMapping("/image/download/{imageId}")
    public ResponseEntity<Resource> downloadImage(@PathVariable Long imageId) throws SQLException {
        Image image = imageService.getImageById(imageId);
        ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1, (int) image.getImage().length()));
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"")
                .body(resource);
    }

    @PutMapping("/image/{imageId/update}")
    public ResponseEntity<ApiResponse> updateImage(@RequestBody MultipartFile file, @PathVariable Long imageId) {
        try {
            Image oldImage = imageService.getImageById(imageId);
            if (null != oldImage) {
                imageService.updateImage(file, imageId);
                return ResponseEntity.ok(new ApiResponse("Update success", null));
            }
        } catch (RessourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Update failed", HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @DeleteMapping("/image/{imageId}/delete")
    public ResponseEntity<?> deleteImage(@PathVariable Long imageId) {
        try {
            Image oldImage = imageService.getImageById(imageId);
            if (null != oldImage) {
                imageService.deleteImageById(imageId);
                return ResponseEntity.ok(new ApiResponse("delete success", null));
            }
        } catch (RessourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
        return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Delete failed", HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
