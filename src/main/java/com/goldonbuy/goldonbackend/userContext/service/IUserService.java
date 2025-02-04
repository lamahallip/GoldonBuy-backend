package com.goldonbuy.goldonbackend.userContext.service;

import com.goldonbuy.goldonbackend.catalogContext.entity.Store;
import com.goldonbuy.goldonbackend.userContext.dto.UserDTO;
import com.goldonbuy.goldonbackend.userContext.entity.User;
import com.goldonbuy.goldonbackend.userContext.request.AddAdminRequest;
import com.goldonbuy.goldonbackend.userContext.request.AddUserRequest;
import com.goldonbuy.goldonbackend.userContext.request.UpdateUserRequest;

public interface IUserService {

    User getUserById(Long userId);
    User getUserByEmail(String email);
    User createUser(AddUserRequest request);
    void userToBecomeAdmin(String email, String storeName);
    User updateUser(UpdateUserRequest request, Long userId);
    void deleteUser(Long userId);

    UserDTO convertToDTO(User user);

    User getAuthenticatedUser();
}
