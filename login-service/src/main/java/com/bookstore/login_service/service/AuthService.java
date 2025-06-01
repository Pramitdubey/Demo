package com.bookstore.login_service.service;

import com.bookstore.login_service.dto.LoginResponse;
import com.bookstore.login_service.dto.LoginRequest;


public interface AuthService {
	LoginResponse login(LoginRequest request);

}
