package com.goldonbuy.goldonbackend.userContext.request;

import com.goldonbuy.goldonbackend.catalogContext.entity.Store;
import lombok.Data;

@Data
public class AddAdminRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Store store;
}
