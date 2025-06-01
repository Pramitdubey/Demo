package com.bookstore.registrationservice.service;

import java.util.Optional;

import com.bookstore.registrationservice.dto.UserRegistrationRequest;
import com.bookstore.registrationservice.entity.User;

public interface UserService {
    String registerUser(UserRegistrationRequest request);
    
    Optional<User> findByEmail(String email);
}
