package com.jewelbankers.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.jewelbankers.entity.Item;
import com.jewelbankers.repository.ItemRepository;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Item getItemById(Long id) {
        return itemRepository.findById(id).orElseThrow(() -> new RuntimeException("Item not found"));
    }

    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }

    public List<Item> searchItems(Map<String, String> searchParams) {
        Specification<Item> spec = (root, query, builder) -> {
            if (searchParams != null && !searchParams.isEmpty()) {
                List<Specification<Item>> specs = searchParams.entrySet().stream()
                        .map(entry -> {
                            String key = entry.getKey();
                            String value = entry.getValue();
                            
                            // Create specifications based on different search parameters
                            if ("name".equals(key)) {
                                return (Specification<Item>) (root1, query1, builder1) -> builder1.like(root1.get("name"), "%" + value + "%");
                            } else if ("type".equals(key)) {
                                return (Specification<Item>) (root1, query1, builder1) -> builder1.like(root1.get("type"), "%" + value + "%");
                            } else if ("weight".equals(key)) {
                                return (Specification<Item>) (root1, query1, builder1) -> builder1.equal(root1.get("weight"), Double.parseDouble(value));
                            } else if ("purity".equals(key)) {
                                return (Specification<Item>) (root1, query1, builder1) -> builder1.equal(root1.get("purity"), new BigDecimal(value));
                            } else if ("quantity".equals(key)) {
                                return (Specification<Item>) (root1, query1, builder1) -> builder1.equal(root1.get("quantity"), Integer.parseInt(value));
                            } else {
                                return null;
                            }
                        })
                        .filter(specification -> specification != null)
                        .collect(Collectors.toList());

                // Combine all specifications using AND if they exist
                if (!specs.isEmpty()) {
                    Specification<Item> resultSpec = specs.get(0);
                    for (int i = 1; i < specs.size(); i++) {
                        resultSpec = Specification.where(resultSpec).and(specs.get(i));
                    }
                    return resultSpec.toPredicate(root, query, builder);
                }
            }
            return builder.conjunction(); // Return all records if no filters are applied
        };

        return itemRepository.findAll(spec);
    }

}
