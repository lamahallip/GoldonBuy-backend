package com.goldonbuy.goldonbackend.userContext.service;

import com.goldonbuy.goldonbackend.catalogContext.entity.Store;
import com.goldonbuy.goldonbackend.catalogContext.exceptions.AlreadyExistingException;
import com.goldonbuy.goldonbackend.catalogContext.exceptions.ResourceNotFoundException;
import com.goldonbuy.goldonbackend.catalogContext.repository.StoreRepository;
import com.goldonbuy.goldonbackend.userContext.dto.UserDTO;
import com.goldonbuy.goldonbackend.userContext.entity.Role;
import com.goldonbuy.goldonbackend.userContext.entity.User;
import com.goldonbuy.goldonbackend.userContext.repository.RoleRepository;
import com.goldonbuy.goldonbackend.userContext.repository.UserRepository;
import com.goldonbuy.goldonbackend.userContext.request.AddAdminRequest;
import com.goldonbuy.goldonbackend.userContext.request.AddUserRequest;
import com.goldonbuy.goldonbackend.userContext.request.UpdateUserRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final StoreRepository storeRepository;

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));
    }

    @Override
    public User getUserByEmail(String email) {
        User user = this.userRepository.findByEmail(email);
        if(user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    @Override
    public User createUser(AddUserRequest request) {

        Role userRole = this.roleRepository.findByName("ROLE_USER");

        return Optional.of(request)
                .filter(user -> !userRepository.existsByEmail(request.getEmail()))
                .map(req -> {
                    User user = new User();
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                    user.setRoles(Set.of(userRole));
                    user.setEmail(request.getEmail());
                    user.setPassword(this.passwordEncoder.encode(request.getPassword()));
                    return userRepository.save(user);
                }).orElseThrow(() -> new AlreadyExistingException("Oop! "+request.getEmail()+" already exists"));
    }

    @Override
    public void userToBecomeAdmin(String email, String storeName) {

        Role adminRole = this.roleRepository.findByName("ROLE_ADMIN");

        Store storeExisting = this.storeRepository.findByName(storeName)
                .orElseThrow(() -> new ResourceNotFoundException("Store not existing"));

        User userExisting = this.userRepository.findByEmail(email);
        if(userExisting == null) {
            throw new ResourceNotFoundException("User not found");
        }
        User userToBecomeAdmin = new User();
        userToBecomeAdmin.setFirstName(userExisting.getFirstName());
        userToBecomeAdmin.setLastName(userExisting.getLastName());
        userToBecomeAdmin.setEmail(userExisting.getEmail());
        userToBecomeAdmin.setStore(storeExisting);
        userToBecomeAdmin.setPassword(userExisting.getPassword());
        userToBecomeAdmin.setRoles(Set.of(adminRole));

        this.userRepository.save(userToBecomeAdmin);
        this.userRepository.delete(userExisting);

    }


    @Override
    public User updateUser(UpdateUserRequest request, Long userId) {
        return userRepository.findById(userId).map(existingUser -> {
            existingUser.setFirstName(request.getFirstName());
            existingUser.setLastName(request.getLastName());
            existingUser.setEmail(request.getEmail());
            existingUser.setPassword(passwordEncoder.encode(request.getPassword()));
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new ResourceNotFoundException("User not found!"));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId).ifPresentOrElse(userRepository::delete, () -> {
            throw new ResourceNotFoundException("User not found!");
        });
    }

    @Override
    public UserDTO convertToDTO(User user) {
        return this.modelMapper.map(user, UserDTO.class);
    }

    @Override
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return this.userRepository.findByEmail(email);
    }
}
