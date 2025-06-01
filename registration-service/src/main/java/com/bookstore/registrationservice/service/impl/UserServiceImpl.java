package com.bookstore.registrationservice.service.impl;

import com.bookstore.registrationservice.dto.UserRegistrationRequest;
import com.bookstore.registrationservice.entity.User;
import com.bookstore.registrationservice.exception.EmailAlreadyExistsException;
import com.bookstore.registrationservice.repository.UserRepository;
import com.bookstore.registrationservice.service.UserService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	 @Autowired
	    private UserRepository userRepository;

	    @Autowired
	    private PasswordEncoder passwordEncoder;

	    public String registerUser(UserRegistrationRequest registrationDto) {
	        if (userRepository.findByEmail(registrationDto.getEmail()).isPresent()) {
	            throw new EmailAlreadyExistsException("Email already exists!");
	        }

	        User user = new User();
	        user.setName(registrationDto.getName());
	        user.setEmail(registrationDto.getEmail());
	        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
	        user.setRole(registrationDto.getRole() != null ? registrationDto.getRole() : "USER");

	        userRepository.save(user);
	        return "User registered successfully!";
	    }

		@Override
		public Optional<User> findByEmail(String email) {
			// TODO Auto-generated method stub
			return userRepository.findByEmail(email);
		}
}
