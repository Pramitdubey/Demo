package com.bookstore.order_service.controller;

import com.bookstore.order_service.dto.*;
import com.bookstore.order_service.entity.Order;
import com.bookstore.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> placeOrder(@RequestBody OrderRequest request,
                                            @RequestHeader("X-User-Email") String userEmail) {
        return ResponseEntity.ok(orderService.placeOrder(request, userEmail));
    }

    @GetMapping("/user")
    public ResponseEntity<List<Order>> getUserOrders(@RequestHeader("X-User-Email") String userEmail) {
        return ResponseEntity.ok(orderService.getOrdersByUser(userEmail));
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders(@RequestHeader("X-User-Role") String role) {
        if (!"ADMIN".equals(role)) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable Long id,
                                             @RequestParam String status,
                                             @RequestHeader("X-User-Role") String role) {
        if (!"ADMIN".equals(role)) {
            return ResponseEntity.status(403).build();
        }
        orderService.updateStatus(id, status);
        return ResponseEntity.ok().build();
    }
    
   
}
