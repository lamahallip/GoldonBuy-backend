package com.goldonbuy.goldonbackend.catalogContext.controller;

import com.goldonbuy.goldonbackend.catalogContext.dto.StoreDTO;
import com.goldonbuy.goldonbackend.catalogContext.entity.Store;
import com.goldonbuy.goldonbackend.catalogContext.exceptions.ResourceNotFoundException;
import com.goldonbuy.goldonbackend.catalogContext.request.AddStoreRequest;
import com.goldonbuy.goldonbackend.catalogContext.request.UpdateStoreRequest;
import com.goldonbuy.goldonbackend.catalogContext.response.ApiResponse;
import com.goldonbuy.goldonbackend.catalogContext.service.store.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/stores")
public class StoreController {

    private final StoreService storeService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addStore(@RequestBody AddStoreRequest request) {
        try {
            Store theStore = this.storeService.addStore(request);
            StoreDTO storeDTO = storeService.convertToDTO(theStore);
            return ResponseEntity.ok(new ApiResponse("Add Store success!", storeDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PutMapping("/store/{storeId}/update")
    public ResponseEntity<ApiResponse> updateStore(@RequestBody UpdateStoreRequest request, @PathVariable Long storeId) {
        try {
            Store newStore = this.storeService.updateStore(request, storeId);
            StoreDTO storeDTO = storeService.convertToDTO(newStore);
            return ResponseEntity.ok(new ApiResponse("Update Store success", storeDTO));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/store/{storeId}/delete")
    public ResponseEntity<ApiResponse> deleteStore(@PathVariable Long storeId) {
        try {
            storeService.deleteStoreById(storeId);
            return ResponseEntity.ok(new ApiResponse("Delete store success", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllStore() {
        List<Store> stores = storeService.getAllStore();
        List<StoreDTO> storeDTOs = storeService.getConvertedStores(stores);
        return ResponseEntity.ok(new ApiResponse("Get all stores success", storeDTOs));
    }

    @GetMapping("/store/{storeId}/store")
    public ResponseEntity<ApiResponse> getStoreById(@PathVariable Long storeId) {
        try {
            Store theStore = storeService.getStoreById(storeId);
            StoreDTO storeDTO = storeService.convertToDTO(theStore);
            return ResponseEntity.ok(new ApiResponse("Success", storeDTO));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/{name}/stores")
    public ResponseEntity<ApiResponse> getStoresByName(@PathVariable String name) {
        try {
            List<Store> stores = storeService.getStoreByName(name);
            List<StoreDTO> storeDTOs = storeService.getConvertedStores(stores);
            return ResponseEntity.ok(new ApiResponse("Success", storeDTOs));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/contactName/{contactName}/stores")
    public ResponseEntity<ApiResponse> getStoresByContactName(@PathVariable String contactName) {
        try {
            List<Store> stores = storeService.getStoreByContactName(contactName);
            List<StoreDTO> storeDTOs = storeService.getConvertedStores(stores);
            return ResponseEntity.ok(new ApiResponse("Success!", storeDTOs));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/by/name-and-contactName")
    public ResponseEntity<ApiResponse> getStoresByNameAndContactName(@RequestParam String name, @RequestParam String contactName) {
        try {
            List<Store> stores = storeService.getStoreByNameAndContactName(name, contactName);
            List<StoreDTO> storeDTOs = storeService.getConvertedStores(stores);
            return ResponseEntity.ok(new ApiResponse("Success !", storeDTOs));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/stores/{type}/stores")
    public ResponseEntity<ApiResponse> getStoresByType(@PathVariable String type) {
        try {
            List<Store> stores = storeService.getStoreByType(type);
            List<StoreDTO> storeDTOs = storeService.getConvertedStores(stores);
            return ResponseEntity.ok(new ApiResponse("Success!", storeDTOs));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/stores/{city}/stores")
    public ResponseEntity<ApiResponse> getStoresByAddressCity(@PathVariable String city) {
        try {
            List<Store> stores = storeService.getStoreByAddressCity(city);
            List<StoreDTO> storeDTOs = storeService.getConvertedStores(stores);
            return ResponseEntity.ok(new ApiResponse("Success!", storeDTOs));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/stores/{street}/stores")
    public ResponseEntity<ApiResponse> getStoresByAddressStreet(@PathVariable String street) {
        try {
            List<Store> stores = storeService.getStoreByAddressStreet(street);
            List<StoreDTO> storeDTOs = storeService.getConvertedStores(stores);
            return ResponseEntity.ok(new ApiResponse("Success!", storeDTOs));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/stores/{country}/stores")
    public ResponseEntity<ApiResponse> getStoresByAddressCountry(@PathVariable String country) {
        try {
            List<Store> stores = storeService.getStoreByAddressCountry(country);
            List<StoreDTO> storeDTOs = storeService.getConvertedStores(stores);
            return ResponseEntity.ok(new ApiResponse("Success!", storeDTOs));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/stores/by/type-and-city")
    public ResponseEntity<ApiResponse> getStoresByTypeAndAddressCity(@RequestParam String type, @RequestParam String city) {
        try {
            List<Store> stores = storeService.getStoreByTypeAndAddressCity(type, city);
            List<StoreDTO> storeDTOs = storeService.getConvertedStores(stores);
            return ResponseEntity.ok(new ApiResponse("Success!", storeDTOs));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/stores/by/type-and-street")
    public ResponseEntity<ApiResponse> getStoresByTypeAndAddressStreet(@RequestParam String type, @RequestParam String street) {
        try {
            List<Store> stores = storeService.getStoreByTypeAndAddressStreet(type, street);
            List<StoreDTO> storeDTOs = storeService.getConvertedStores(stores);
            return ResponseEntity.ok(new ApiResponse("Success!", storeDTOs));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/stores/by/type-and-street-and-city")
    public ResponseEntity<ApiResponse> getStoreByTypeAndAddressStreetAndAddressCity(@RequestParam String type, @RequestParam String street, @RequestParam String city) {
        try {
            List<Store> stores = storeService.getStoreByTypeAndAddressStreetAndAddressCity(type, street, city);
            List<StoreDTO> storeDTOs = storeService.getConvertedStores(stores);
            return ResponseEntity.ok(new ApiResponse("Success!", storeDTOs));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/stores/by-name/and-contactName")
    public ResponseEntity<ApiResponse> countStoreByNameAndContactName(@RequestParam String name, @RequestParam String contactName) {
        try {
            var storeCount = storeService.countStoreByNameAndContactName(name, contactName);
            return ResponseEntity.ok(new ApiResponse("Success!", storeCount));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

}
