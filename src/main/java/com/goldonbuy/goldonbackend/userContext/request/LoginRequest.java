package com.goldonbuy.goldonbackend.userContext.request;

import lombok.Data;
import lombok.NonNull;

@Data
public class LoginRequest {

    @NonNull
    private String email;

    @NonNull
    private String password;
}
