package com.bookstore.inventory_service.service;


import com.bookstore.inventory_service.dto.InventoryRequest;
import com.bookstore.inventory_service.entity.Inventory;
import com.bookstore.inventory_service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public Inventory getByBookId(Long bookId) {
        return inventoryRepository.findByBookId(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found in inventory"));
    }

    public List<Inventory> getAll() {
        return inventoryRepository.findAll();
    }

    public Inventory addStock(Long bookId, Integer quantity) {
        Inventory inv = inventoryRepository.findByBookId(bookId)
                .orElse(Inventory.builder().bookId(bookId).quantityAvailable(0).build());

        inv.setQuantityAvailable(inv.getQuantityAvailable() + quantity);
        return inventoryRepository.save(inv);
    }

    public boolean reduceStock(List<InventoryRequest> requests) {
        for (InventoryRequest req : requests) {
            Inventory inv = inventoryRepository.findByBookId(req.getBookId())
                    .orElseThrow(() -> new RuntimeException("Book not in inventory"));

            if (inv.getQuantityAvailable() < req.getQuantity()) {
                return false; // insufficient stock
            }
        }

        // Reduce stock if all are available
        for (InventoryRequest req : requests) {
            Inventory inv = inventoryRepository.findByBookId(req.getBookId()).get();
            inv.setQuantityAvailable(inv.getQuantityAvailable() - req.getQuantity());
            inventoryRepository.save(inv);
        }

        return true;
    }
}
