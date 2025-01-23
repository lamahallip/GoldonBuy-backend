package com.goldonbuy.goldonbackend.userContext.security.authentication.service;

import com.goldonbuy.goldonbackend.userContext.request.LoginRequest;

public interface IAuthService {
    String login(LoginRequest request);
}
