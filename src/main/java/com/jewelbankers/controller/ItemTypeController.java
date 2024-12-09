//package com.jewelbankers.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import com.jewelbankers.entity.ItemType;
//import com.jewelbankers.services.ItemTypeService;
//
//@RestController
//@RequestMapping("/jewelbankersapi/item_types")
//@CrossOrigin(origins = "http://localhost:4200")
//public class ItemTypeController {
//
//    @Autowired
//    private ItemTypeService itemTypeService;
//
//    @GetMapping
//    public ResponseEntity<?> getAllItemTypes() {
//        try {
//            List<ItemType> itemTypes = itemTypeService.getAllItemTypes();
//            if (itemTypes.isEmpty()) {
//                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No item types found.");
//            }
//            return ResponseEntity.ok(itemTypes);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                                 .body("Error retrieving item types: " + e.getMessage());
//        }
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<?> getItemTypeById(@PathVariable Integer id) {
//        try {
//            ItemType itemType = itemTypeService.getItemTypeById(id);
//            if (itemType != null) {
//                return ResponseEntity.ok(itemType);
//            } else {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                                     .body("Item type not found with ID: " + id);
//            }
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                                 .body("Error retrieving item type: " + e.getMessage());
//        }
//    }
//}
