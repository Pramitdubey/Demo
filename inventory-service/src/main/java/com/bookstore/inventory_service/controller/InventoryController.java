package com.bookstore.inventory_service.controller;

import com.bookstore.inventory_service.dto.InventoryRequest;
import com.bookstore.inventory_service.entity.Inventory;
import com.bookstore.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/book/{bookId}")
    public ResponseEntity<Inventory> getStock(@PathVariable Long bookId) {
        return ResponseEntity.ok(inventoryService.getByBookId(bookId));
    }

    @PutMapping("/add/{bookId}")
    public ResponseEntity<Inventory> addStock(@PathVariable Long bookId,
                                              @RequestParam Integer quantity) {
        return ResponseEntity.ok(inventoryService.addStock(bookId, quantity));
    }

    @PostMapping("/reduce")
    public ResponseEntity<Boolean> reduceStock(@RequestBody List<InventoryRequest> requests) {
        boolean success = inventoryService.reduceStock(requests);
        if (!success) return ResponseEntity.badRequest().body(false);
        return ResponseEntity.ok(true);
    }

    @GetMapping
    public ResponseEntity<List<Inventory>> getAll() {
        return ResponseEntity.ok(inventoryService.getAll());
    }
}