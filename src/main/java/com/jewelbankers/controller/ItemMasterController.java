package com.jewelbankers.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jewelbankers.entity.ItemMaster;
import com.jewelbankers.services.ItemMasterService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/jewelbankersapi/item-master")
public class ItemMasterController {

    @Autowired
    private ItemMasterService itemMasterService;

    @GetMapping
    public List<ItemMaster> getAllItems() {
        return itemMasterService.getAllItems();
    }

    @GetMapping("/{id}")
    public Optional<ItemMaster> getItemById(@PathVariable Long id) {
        return itemMasterService.getItemById(id);
    }

    @PostMapping
    public ItemMaster createItem(@RequestBody ItemMaster itemMaster) {
        return itemMasterService.createOrUpdateItem(itemMaster);
    }

    @PutMapping("/{id}")
    public ItemMaster updateItem(@PathVariable Long id, @RequestBody ItemMaster itemMaster) {
        itemMaster.setId(id);
        return itemMasterService.createOrUpdateItem(itemMaster);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) {
        itemMasterService.deleteItem(id);
    }
    
    @GetMapping("/search")
    public List<ItemMaster> searchItems(@RequestParam String search) {
        return itemMasterService.searchItems(search);
    }
}
