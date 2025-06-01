package com.bookstore.order_service.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import com.bookstore.order_service.dto.OrderItemDTO;

@Data
public class OrderResponse {
    private Long id;
    private String userEmail;
    private LocalDateTime orderDate;
    private String status;
    private Double totalAmount;
    private List<OrderItemDTO> items;
}
