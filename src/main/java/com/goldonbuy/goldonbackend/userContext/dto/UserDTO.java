package com.goldonbuy.goldonbackend.userContext.dto;

import com.goldonbuy.goldonbackend.catalogContext.dto.CartDTO;
import com.goldonbuy.goldonbackend.catalogContext.dto.OrderDTO;
import com.goldonbuy.goldonbackend.catalogContext.entity.Store;
import com.goldonbuy.goldonbackend.userContext.entity.Role;
import lombok.Data;

import java.util.Collection;
import java.util.List;

@Data
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Collection<Role> roles;
    private Store store;
    private List<OrderDTO> orders;
    private CartDTO cart;
}
