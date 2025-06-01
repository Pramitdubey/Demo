package com.bookstore.order_service.dto;

import lombok.Data;

@Data
public class OrderItemDTO {
    private Long bookId;
    private Integer quantity;
    private Double price; // Cached price
}
