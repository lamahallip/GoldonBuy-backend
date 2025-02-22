package com.goldonbuy.goldonbackend.userContext.repository;

import com.goldonbuy.goldonbackend.userContext.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByEmail(String email);
    User findByEmail(String email);

}
