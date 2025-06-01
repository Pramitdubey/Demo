package com.bookstore.login_service.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.bookstore.login_service.client.UserClient;
import com.bookstore.login_service.dto.LoginRequest;
import com.bookstore.login_service.dto.LoginResponse;
import com.bookstore.login_service.dto.UserDTO;
import com.bookstore.login_service.service.AuthService;

@RestController
@RequestMapping("/api/login")
public class AuthController {

    @Autowired
    private AuthService authService;
    

    @PostMapping
    public LoginResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}