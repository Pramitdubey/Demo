package com.bookstore.order_service.dto;

import lombok.Data;
import java.util.List;
import com.bookstore.order_service.dto.OrderItemDTO;

@Data
public class OrderRequest {
    private List<OrderItemDTO> items;
}



