//package com.jewelbankers.controller;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import com.jewelbankers.entity.ItemMaster;
//import com.jewelbankers.services.ItemMasterService;
//
//@RestController
//@CrossOrigin(origins = "http://localhost:4200")
//@RequestMapping("/jewelbankersapi/item-master")
//public class ItemMasterController {
//
//    @Autowired
//    private ItemMasterService itemMasterService;
//
//    @GetMapping
//    public ResponseEntity<?> getAllItems() {
//        try {
//            List<ItemMaster> items = itemMasterService.getAllItems();
//            if (items.isEmpty()) {
//                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No items found.");
//            }
//            return ResponseEntity.ok(items);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                                 .body("Error retrieving items: " + e.getMessage());
//        }
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<?> getItemById(@PathVariable Long id) {
//        try {
//            Optional<ItemMaster> item = itemMasterService.getItemById(id);
//            if (item.isPresent()) {
//                return ResponseEntity.ok(item.get());
//            } else {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                                     .body("Item not found with ID: " + id);
//            }
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                                 .body("Error retrieving item: " + e.getMessage());
//        }
//    }
//
//    @PostMapping
//    public ResponseEntity<?> createItem(@RequestBody ItemMaster itemMaster) {
//        try {
//            ItemMaster createdItem = itemMasterService.createOrUpdateItem(itemMaster);
//            return ResponseEntity.status(HttpStatus.CREATED)
//                                 .body("Item created successfully with ID: " + createdItem.getId());
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                                 .body("Error creating item: " + e.getMessage());
//        }
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<?> updateItem(@PathVariable Long id, @RequestBody ItemMaster itemMaster) {
//        try {
//            if (!itemMasterService.getItemById(id).isPresent()) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                                     .body("Cannot update. Item not found with ID: " + id);
//            }
//            itemMaster.setId(id);
//            ItemMaster updatedItem = itemMasterService.createOrUpdateItem(itemMaster);
//            return ResponseEntity.ok("Item updated successfully with ID: " + updatedItem.getId());
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                                 .body("Error updating item: " + e.getMessage());
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteItem(@PathVariable Long id) {
//        try {
//            if (!itemMasterService.getItemById(id).isPresent()) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                                     .body("Cannot delete. Item not found with ID: " + id);
//            }
//            itemMasterService.deleteItem(id);
//            return ResponseEntity.ok("Item deleted successfully with ID: " + id);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                                 .body("Error deleting item: " + e.getMessage());
//        }
//    }
//
//    @GetMapping("/search")
//    public ResponseEntity<?> searchItems(@RequestParam String search) {
//        try {
//            List<ItemMaster> items = itemMasterService.searchItems(search);
//            if (items.isEmpty()) {
//                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No items match your search criteria.");
//            }
//            return ResponseEntity.ok(items);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                                 .body("Error searching for items: " + e.getMessage());
//        }
//    }
//}
