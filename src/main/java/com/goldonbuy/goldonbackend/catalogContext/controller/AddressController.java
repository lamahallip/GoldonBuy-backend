package com.goldonbuy.goldonbackend.catalogContext.controller;

import com.goldonbuy.goldonbackend.catalogContext.dto.AddressDTO;
import com.goldonbuy.goldonbackend.catalogContext.entity.Address;
import com.goldonbuy.goldonbackend.catalogContext.exceptions.ResourceNotFoundException;
import com.goldonbuy.goldonbackend.catalogContext.request.AddAddressRequest;
import com.goldonbuy.goldonbackend.catalogContext.request.UpdateAddressRequest;
import com.goldonbuy.goldonbackend.catalogContext.response.ApiResponse;
import com.goldonbuy.goldonbackend.catalogContext.service.address.IAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/address")
public class AddressController {

    private final IAddressService addressService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addAddress(@RequestBody AddAddressRequest request) {
        try {
            Address theAddress = this.addressService.addAddress(request);
            AddressDTO addressDTO = addressService.convertToDTO(theAddress);
            return ResponseEntity.ok(new ApiResponse("Add address success", addressDTO));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/address/{productId}/update")
    public ResponseEntity<ApiResponse> updateAddress(@RequestBody UpdateAddressRequest request, @PathVariable Long productId) {
        try {
            Address theAddress = this.addressService.updateAddress(request, productId);
            AddressDTO addressDTO = addressService.convertToDTO(theAddress);
            return ResponseEntity.ok(new ApiResponse("Update address success", addressDTO));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
