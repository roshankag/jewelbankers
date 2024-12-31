package com.jewelbankers.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jewelbankers.entity.Item;
import com.jewelbankers.entity.ItemType;
import com.jewelbankers.services.ItemTypeService;

@RestController
@RequestMapping("/jewelbankersapi/item/types")
@CrossOrigin(origins = "http://localhost:4200")
public class ItemTypeController {

    @Autowired
    private ItemTypeService itemTypeService;

    // Create new ItemType
    @PostMapping
    public ResponseEntity<?> createItemType(@RequestBody ItemType itemType) {
        try {
            ItemType createdItemType = itemTypeService.saveItemType(itemType);
            return ResponseEntity.status(HttpStatus.CREATED)
                                 .body("Item type created successfully: " + createdItemType);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Error creating item type: " + e.getMessage());
        }
    }

    // Update ItemType
    @PutMapping("/{id}")
    public ResponseEntity<?> updateItemType(@PathVariable Long id, @RequestBody ItemType itemType) {
        try {
            itemType.setId(id);
            ItemType updatedItemType = itemTypeService.saveItemType(itemType);
            return ResponseEntity.ok("Item type updated successfully: " + updatedItemType);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Error updating item type: " + e.getMessage());
        }
    }

    // Delete ItemType
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteItemType(@PathVariable Long id) {
        try {
            itemTypeService.deleteItemType(id);
            return ResponseEntity.status(HttpStatus.OK)
                                 .body("Item type deleted successfully with ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error deleting item type: " + e.getMessage());
        }
    }

    // Get ItemType by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getItemTypeById(@PathVariable Long id) {
        try {
            ItemType itemType = itemTypeService.getItemTypeById(id);
            if (itemType != null) {
                return ResponseEntity.ok("Item type found: " + itemType);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                     .body("Item type not found for ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error retrieving item type: " + e.getMessage());
        }
    }

    // Get all ItemTypes
    @GetMapping
    public ResponseEntity<?> getAllItemTypes() {
        try {
            Iterable<ItemType> itemTypes = itemTypeService.getAllItemTypes();
            if (itemTypes != null) {
                return ResponseEntity.ok(itemTypes);
            }
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No items found.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error retrieving items: " + e.getMessage());
        }
        
    }
}
