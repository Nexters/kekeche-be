package com.nexters.kekechebe.domain.auth.service;

import com.nexters.kekechebe.domain.auth.dto.response.LoginResponse;

public interface AuthService {
    LoginResponse login(String code);
}
