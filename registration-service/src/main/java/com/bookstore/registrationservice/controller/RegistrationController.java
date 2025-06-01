package com.bookstore.registrationservice.controller;

import com.bookstore.registrationservice.dto.UserRegistrationRequest;
import com.bookstore.registrationservice.service.UserService;
import com.bookstore.registrationservice.entity.User;

import jakarta.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/api/registration")
public class RegistrationController {

    @Autowired
    private UserService userService;
    
    @GetMapping("/email/{email}")
    public UserRegistrationRequest getUserByEmail(@PathVariable String email) {
        // Safely get the User object from Optional, or throw 404 if not found
        User user = userService.findByEmail(email)
                               .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with email: " + email));

        
        return new UserRegistrationRequest(user.getName(), user.getEmail(), user.getPassword(), user.getRole());
    }

    @PostMapping
    public ResponseEntity<String> registerUser( @Valid @RequestBody UserRegistrationRequest request) {
        userService.registerUser(request);
        return ResponseEntity.ok("User registered successfully");
    }
    
    
}
