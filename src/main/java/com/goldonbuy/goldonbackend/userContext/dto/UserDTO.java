package com.goldonbuy.goldonbackend.userContext.dto;

import com.goldonbuy.goldonbackend.catalogContext.dto.CartDTO;
import com.goldonbuy.goldonbackend.catalogContext.dto.OrderDTO;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<OrderDTO> orders;
    private CartDTO cart;
}
