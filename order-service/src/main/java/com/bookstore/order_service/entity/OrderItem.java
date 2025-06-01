package com.bookstore.order_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder

public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long bookId;
    private Integer quantity;
    private Double price;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}