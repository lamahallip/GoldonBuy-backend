package com.goldonbuy.goldonbackend.userContext.controller;

import com.goldonbuy.goldonbackend.catalogContext.exceptions.AlreadyExistingException;
import com.goldonbuy.goldonbackend.catalogContext.exceptions.ResourceNotFoundException;
import com.goldonbuy.goldonbackend.catalogContext.response.ApiResponse;
import com.goldonbuy.goldonbackend.userContext.dto.UserDTO;
import com.goldonbuy.goldonbackend.userContext.entity.User;
import com.goldonbuy.goldonbackend.userContext.request.AddUserRequest;
import com.goldonbuy.goldonbackend.userContext.request.UpdateUserRequest;
import com.goldonbuy.goldonbackend.userContext.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {

    private final IUserService userService;

    @GetMapping("/{userId}/user")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId) {
        try {
            User user = this.userService.getUserById(userId);
            UserDTO userDTO = this.userService.convertToDTO(user);
            return ResponseEntity.ok(new ApiResponse("Success !", userDTO));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createUser(@RequestBody AddUserRequest request) {
        try {
            User user = this.userService.createUser(request);
            UserDTO userDTO = this.userService.convertToDTO(user);
            return ResponseEntity.ok(new ApiResponse("Create User success !", userDTO));
        } catch (AlreadyExistingException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/{userId}/update")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UpdateUserRequest request, @PathVariable Long userId) {
        try {
            User user = this.userService.updateUser(request, userId);
            UserDTO userDTO = this.userService.convertToDTO(user);
            return ResponseEntity.ok(new ApiResponse("Update User success !", userDTO));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId) {
        try {
            this.userService.deleteUser(userId);
            return ResponseEntity.ok(new ApiResponse("Delete Success !", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }


}
