package com.goldonbuy.goldonbackend.catalogContext.controller;

import com.goldonbuy.goldonbackend.catalogContext.entity.Address;
import com.goldonbuy.goldonbackend.catalogContext.exceptions.RessourceNotFoundException;
import com.goldonbuy.goldonbackend.catalogContext.requestDTO.AddAddressRequest;
import com.goldonbuy.goldonbackend.catalogContext.requestDTO.UpdateAddressRequest;
import com.goldonbuy.goldonbackend.catalogContext.response.ApiResponse;
import com.goldonbuy.goldonbackend.catalogContext.service.address.IAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/address")
public class AddressController {

    private final IAddressService addressService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addAddress(@RequestBody AddAddressRequest request) {
        try {
            Address theAddress = this.addressService.addAddress(request);
            return ResponseEntity.ok(new ApiResponse("Add address success", theAddress));
        } catch (RessourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/address/{productId}/update")
    public ResponseEntity<ApiResponse> updateAddress(@RequestBody UpdateAddressRequest request, @PathVariable Long productId) {
        try {
            Address theAddress = this.addressService.updateAddress(request, productId);
            return ResponseEntity.ok(new ApiResponse("Update address success", theAddress));
        } catch (RessourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
