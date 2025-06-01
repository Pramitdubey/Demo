package com.bookstore.login_service.service.impl;

import com.bookstore.login_service.dto.LoginRequest;
import com.bookstore.login_service.dto.LoginResponse;
import com.bookstore.login_service.dto.UserDTO;
import com.bookstore.login_service.service.AuthService;
import com.bookstore.login_service.security.JwtUtil;
import com.bookstore.login_service.client.UserClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
	
	 @Autowired
	    private UserClient userClient;

	    @Autowired
	    private JwtUtil jwtUtil;

	    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	    public LoginResponse login(LoginRequest request) {
	        UserDTO user = userClient.getUserByEmail(request.getEmail());

	        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
	            throw new RuntimeException("Invalid email or password");
	        }

	        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());

	        return new LoginResponse(token, user.getRole());
	    }

	

}
