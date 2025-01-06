package com.goldonbuy.goldonbackend.catalogContext.controller;

import com.goldonbuy.goldonbackend.catalogContext.entity.Store;
import com.goldonbuy.goldonbackend.catalogContext.exceptions.RessourceNotFoundException;
import com.goldonbuy.goldonbackend.catalogContext.requestDTO.AddStoreRequest;
import com.goldonbuy.goldonbackend.catalogContext.requestDTO.UpdateStoreRequest;
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
            return ResponseEntity.ok(new ApiResponse("Add Store success!", theStore));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PutMapping("/store/{storeId}/update")
    public ResponseEntity<ApiResponse> updateStore(@RequestBody UpdateStoreRequest request, @PathVariable Long storeId) {
        try {
            Store newStore = this.storeService.updateStore(request, storeId);
            return ResponseEntity.ok(new ApiResponse("Update Store success", newStore));
        } catch (RessourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/store/{storeId}/delete")
    public ResponseEntity<ApiResponse> deleteStore(@PathVariable Long storeId) {
        try {
            storeService.deleteStoreById(storeId);
            return ResponseEntity.ok(new ApiResponse("Delete store success", null));
        } catch (RessourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllStore() {
        List<Store> stores = storeService.getAllStore();
        return ResponseEntity.ok(new ApiResponse("Get all stores success", stores));
    }

    @GetMapping("/store/{storeId}/store")
    public ResponseEntity<ApiResponse> getStoreById(@PathVariable Long storeId) {
        try {
            Store theStore = storeService.getStoreById(storeId);
            return ResponseEntity.ok(new ApiResponse("Success", theStore));
        } catch (RessourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/stores/{name}/stores")
    public ResponseEntity<ApiResponse> getStoresByName(@PathVariable String name) {
        try {
            List<Store> stores = storeService.getStoreByName(name);
            return ResponseEntity.ok(new ApiResponse("Success", stores));
        } catch (RessourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/stores/{contactName}/stores")
    public ResponseEntity<ApiResponse> getStoresByContactName(@PathVariable String contactName) {
        try {
            List<Store> stores = storeService.getStoreByContactName(contactName);
            return ResponseEntity.ok(new ApiResponse("Success!", stores));
        } catch (RessourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/stores/by/name-and-contactName")
    public ResponseEntity<ApiResponse> getStoresByNameAndContactName(@RequestParam String name, @RequestParam String contactName) {
        try {
            List<Store> stores = storeService.getStoreByNameAndContactName(name, contactName);
            return ResponseEntity.ok(new ApiResponse("Success!", stores));
        } catch (RessourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/stores/{type}/stores")
    public ResponseEntity<ApiResponse> getStoresByType(@PathVariable String type) {
        try {
            List<Store> stores = storeService.getStoreByType(type);
            return ResponseEntity.ok(new ApiResponse("Success!", stores));
        } catch (RessourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/stores/{city}/stores")
    public ResponseEntity<ApiResponse> getStoresByAddressCity(@PathVariable String city) {
        try {
            List<Store> stores = storeService.getStoreByAddressCity(city);
            return ResponseEntity.ok(new ApiResponse("Success!", stores));
        } catch (RessourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/stores/{street}/stores")
    public ResponseEntity<ApiResponse> getStoresByAddressStreet(@PathVariable String street) {
        try {
            List<Store> stores = storeService.getStoreByAddressStreet(street);
            return ResponseEntity.ok(new ApiResponse("Success!", stores));
        } catch (RessourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/stores/{country}/stores")
    public ResponseEntity<ApiResponse> getStoresByAddressCountry(@PathVariable String country) {
        try {
            List<Store> stores = storeService.getStoreByAddressCountry(country);
            return ResponseEntity.ok(new ApiResponse("Success!", stores));
        } catch (RessourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/stores/by/type-and-city")
    public ResponseEntity<ApiResponse> getStoresByTypeAndAddressCity(@RequestParam String type, @RequestParam String city) {
        try {
            List<Store> stores = storeService.getStoreByTypeAndAddressCity(type, city);
            return ResponseEntity.ok(new ApiResponse("Success!", stores));
        } catch (RessourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/stores/by/type-and-street")
    public ResponseEntity<ApiResponse> getStoresByTypeAndAddressStreet(@RequestParam String type, @RequestParam String street) {
        try {
            List<Store> stores = storeService.getStoreByTypeAndAddressStreet(type, street);
            return ResponseEntity.ok(new ApiResponse("Success!", stores));
        } catch (RessourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/stores/by/type-and-street-and-city")
    public ResponseEntity<ApiResponse> getStoreByTypeAndAddressStreetAndAddressCity(@RequestParam String type, @RequestParam String street, @RequestParam String city) {
        try {
            List<Store> stores = storeService.getStoreByTypeAndAddressStreetAndAddressCity(type, street, city);
            return ResponseEntity.ok(new ApiResponse("Success!", stores));
        } catch (RessourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }




}
