package com.bookstore.order_service.client;

import com.bookstore.order_service.dto.InventoryRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "inventory-service")
public interface InventoryClient {

    @PostMapping("/api/inventory/reduce")
    ResponseEntity<Boolean> reduceStock(@RequestBody List<InventoryRequest> requestList);
}
