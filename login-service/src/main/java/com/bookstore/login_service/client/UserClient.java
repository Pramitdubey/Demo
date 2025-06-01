package com.bookstore.login_service.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.bookstore.login_service.dto.UserDTO;

@FeignClient(name = "registration-service") // registered with Eureka
public interface UserClient {

    @GetMapping("api/registration/email/{email}")
    UserDTO getUserByEmail(@PathVariable String email);
}