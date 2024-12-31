package com.jewelbankers.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jewelbankers.entity.ItemType;
import com.jewelbankers.repository.ItemTypeRepository;

@Service
public class ItemTypeService {

    @Autowired
    private ItemTypeRepository itemTypeRepository;

    public ItemType saveItemType(ItemType itemType) {
        return itemTypeRepository.save(itemType);
    }

    public void deleteItemType(Long id) {
        itemTypeRepository.deleteById(id);
    }

    public ItemType getItemTypeById(Long id) {
        return itemTypeRepository.findById(id).orElse(null);
    }

    public Iterable<ItemType> getAllItemTypes() {
        return itemTypeRepository.findAll();
    }
}
