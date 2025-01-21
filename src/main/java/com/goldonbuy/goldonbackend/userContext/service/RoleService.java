package com.goldonbuy.goldonbackend.userContext.service;


import com.goldonbuy.goldonbackend.userContext.entity.Role;
import com.goldonbuy.goldonbackend.userContext.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role getRoleByName(String name) {
        return Optional.ofNullable(this.roleRepository.findByName(name))
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setName(name);
                    return this.roleRepository.save(newRole);
                });
    }
}
