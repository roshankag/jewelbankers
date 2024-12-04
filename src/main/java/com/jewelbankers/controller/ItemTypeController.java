package com.jewelbankers.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jewelbankers.entity.ItemType;
import com.jewelbankers.services.ItemTypeService;

@RestController
@RequestMapping("/jewelbankersapi/item_types")
@CrossOrigin(origins = "http://localhost:4200")
public class ItemTypeController {

    @Autowired
    private ItemTypeService itemTypeService;

    @GetMapping
    public List<ItemType> getAllItemTypes() {
        return itemTypeService.getAllItemTypes();
    }

    @GetMapping("/{id}")
    public ItemType getItemTypeById(@PathVariable Integer id) {
        return itemTypeService.getItemTypeById(id);
    }
}
