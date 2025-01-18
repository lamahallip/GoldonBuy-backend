package com.goldonbuy.goldonbackend.userContext.request;

import lombok.Data;


@Data
public class AddUserRequest {
    private String firstName;
    private String lastName;
    private String password;
    private String email;
}
