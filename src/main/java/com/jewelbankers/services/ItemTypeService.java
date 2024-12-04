package com.jewelbankers.services;

import com.jewelbankers.entity.ItemType;
import com.jewelbankers.repository.ItemTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemTypeService {

    @Autowired
    private ItemTypeRepository itemTypeRepository;

    public List<ItemType> getAllItemTypes() {
        return itemTypeRepository.findAll();
    }

    public ItemType getItemTypeById(Integer id) {
        return itemTypeRepository.findById(id).orElseThrow(() -> new RuntimeException("Item Type not found"));
    }
}
