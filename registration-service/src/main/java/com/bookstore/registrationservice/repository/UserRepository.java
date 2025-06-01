package com.bookstore.registrationservice.repository;

import com.bookstore.registrationservice.entity.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	 Optional<User> findByEmail(String email);  // For checking duplicate emails
}
