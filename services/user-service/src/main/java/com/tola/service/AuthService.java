package com.tola.service;

import com.tola.payload.dto.UserDTO;
import com.tola.payload.response.AuthResponse;

public interface AuthService {
    AuthResponse signup(UserDTO request) throws Exception;

    AuthResponse login(String email, String password) throws Exception;
}
