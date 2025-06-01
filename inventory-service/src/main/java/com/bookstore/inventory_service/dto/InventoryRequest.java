package com.bookstore.inventory_service.dto;

import lombok.Data;

@Data
public class InventoryRequest {
    private Long bookId;
    private Integer quantity;
}
