package com.goldonbuy.goldonbackend.catalogContext.controller;

import com.goldonbuy.goldonbackend.catalogContext.entity.Cart;
import com.goldonbuy.goldonbackend.catalogContext.exceptions.ResourceNotFoundException;
import com.goldonbuy.goldonbackend.catalogContext.response.ApiResponse;
import com.goldonbuy.goldonbackend.catalogContext.service.cart.ICartItemService;
import com.goldonbuy.goldonbackend.catalogContext.service.cart.ICartService;
import com.goldonbuy.goldonbackend.userContext.entity.User;
import com.goldonbuy.goldonbackend.userContext.service.IUserService;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {

    private final ICartItemService cartItemService;
    private final ICartService cartService;
    private final IUserService userService;

    @PostMapping("/item/add")
    public ResponseEntity<ApiResponse> addItemToCart(// @RequestParam(required = false) Long cartId,
                                                     @RequestParam Long productId,
                                                     @RequestParam Integer quantity) {
        try {
            User user = this.userService.getAuthenticatedUser();
            Cart cart = this.cartService.initializeNewCart(user);

            this.cartItemService.addItemToCart(cart.getId(), productId, quantity);
            return ResponseEntity.ok(new ApiResponse("Add item success !", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        } catch (JwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/cart/{cartId}/item/{itemId}/remove")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable Long cartId, @PathVariable Long itemId) {
        try {
            this.cartItemService.removeItemToCart(cartId, itemId);
            return ResponseEntity.ok(new ApiResponse("Remove item success !", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/cart/{cartId}/item/{itemId}/update")
    public ResponseEntity<ApiResponse> updateItemQuantity(@PathVariable Long cartId,
                                                          @PathVariable Long itemId,
                                                          @RequestParam Integer quantity) {
        try {
            this.cartItemService.updateItemQuantity(cartId, itemId, quantity);
            return ResponseEntity.ok(new ApiResponse("Update item success !", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
