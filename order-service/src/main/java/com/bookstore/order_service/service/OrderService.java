package com.bookstore.order_service.service;

import com.bookstore.order_service.dto.OrderRequest;
import com.bookstore.order_service.dto.InventoryRequest;
import com.bookstore.order_service.client.InventoryClient;
import com.bookstore.order_service.entity.*;
import com.bookstore.order_service.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    
    private final InventoryClient inventoryClient;

    public Order placeOrder(OrderRequest request, String userEmail) {
        // Step 1: Convert DTO to OrderItems
        List<OrderItem> items = request.getItems().stream().map(dto ->
                OrderItem.builder()
                        .bookId(dto.getBookId())
                        .quantity(dto.getQuantity())
                        .price(dto.getPrice())
                        .build()
        ).collect(Collectors.toList());

        // Step 2: Prepare inventory request
        List<InventoryRequest> inventoryRequests = items.stream().map(i -> {
            InventoryRequest inv = new InventoryRequest();
            inv.setBookId(i.getBookId());
            inv.setQuantity(i.getQuantity());
            return inv;
        }).collect(Collectors.toList());

        // Step 3: Call Inventory Service
        Boolean reduced = inventoryClient.reduceStock(inventoryRequests).getBody();
        if (Boolean.FALSE.equals(reduced)) {
            throw new RuntimeException("Inventory not sufficient for one or more books");
        }

        // Step 4: Compute total
        double total = items.stream()
                .mapToDouble(i -> i.getPrice() * i.getQuantity())
                .sum();

        // Step 5: Build order object
        Order order = Order.builder()
                .userEmail(userEmail)
                .orderDate(LocalDateTime.now())
                .status("PENDING")
                .totalAmount(total)
                .items(items)
                .build();

        items.forEach(i -> i.setOrder(order));

        // Step 6: Save order
        return orderRepository.save(order);
    }

    public List<Order> getOrdersByUser(String email) {
        return orderRepository.findByUserEmail(email);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public void updateStatus(Long id, String status) {
        Order order = orderRepository.findById(id).orElseThrow();
        order.setStatus(status);
        orderRepository.save(order);
    }
    
}
