package com.project.library_management.service;

import com.project.library_management.payload.request.auth.LoginRequest;
import com.project.library_management.payload.request.auth.RegisterRequest;
import com.project.library_management.payload.response.auth.AuthResponse;

public interface AuthenticationService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

}
