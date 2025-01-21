package com.goldonbuy.goldonbackend.userContext.repository;

import com.goldonbuy.goldonbackend.userContext.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
