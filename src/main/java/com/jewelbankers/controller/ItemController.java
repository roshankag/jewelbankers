package com.jewelbankers.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jewelbankers.entity.Item;
import com.jewelbankers.services.ItemService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/jewelbankersapi/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping
    public ResponseEntity<?> getAllItems() {
        try {
            List<Item> items = itemService.getAllItems();
            if (items.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No items found.");
            }
            return ResponseEntity.ok(items);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error retrieving items: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getItemById(@PathVariable Long id) {
        try {
            Item item = itemService.getItemById(id);
            if (item != null) {
                return ResponseEntity.ok(item);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                     .body("Item not found with ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error retrieving item: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createItem(@RequestBody Item item) {
        try {
            Item createdItem = itemService.saveItem(item);
            return ResponseEntity.status(HttpStatus.CREATED)
                                 .body("Item created successfully with ID: " + createdItem.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Error creating item: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateItem(@PathVariable Long id, @RequestBody Item item) {
        try {
            item.setId(id);
            Item updatedItem = itemService.saveItem(item);
            return ResponseEntity.ok("Item updated successfully with ID: " + updatedItem.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Error updating item: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable Long id) {
        try {
            itemService.deleteItem(id);
            return ResponseEntity.status(HttpStatus.OK)
                                 .body("Item deleted successfully with ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error deleting item: " + e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchItems(@RequestParam Map<String, String> search) {
        try {
            List<Item> items = itemService.searchItems(search);
            if (items.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No items match the search criteria.");
            }
            return ResponseEntity.ok(items);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error searching for items: " + e.getMessage());
        }
    }
}
