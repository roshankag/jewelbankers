package com.jewelbankers.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.jewelbankers.Utility.SearchSpecification;
import com.jewelbankers.entity.ItemMaster;
import com.jewelbankers.repository.ItemMasterRepository;

@Service
public class ItemMasterService {

    @Autowired
    private ItemMasterRepository itemMasterRepository;

    public List<ItemMaster> getAllItems() {
        return itemMasterRepository.findAll();
    }

    public Optional<ItemMaster> getItemById(Long id) {
        return itemMasterRepository.findById(id);
    }

    public ItemMaster createOrUpdateItem(ItemMaster itemMaster) {
        return itemMasterRepository.save(itemMaster);
    }

    public void deleteItem(Long id) {
        itemMasterRepository.deleteById(id);
    }
    
    public List<ItemMaster> searchItems(String search) {
        Specification<ItemMaster> specification = SearchSpecification.searchByTerm(search);
        return itemMasterRepository.findAll(specification);
    }

}
