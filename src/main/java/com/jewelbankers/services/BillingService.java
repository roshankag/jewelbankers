package com.jewelbankers.services;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.jewelbankers.Utility.SettingsUtillity;
import com.jewelbankers.entity.Customer;
import com.jewelbankers.entity.ItemType;
import com.jewelbankers.entity.Jewel;
import com.jewelbankers.entity.JewelDetail;
import com.jewelbankers.entity.Settings;
import com.jewelbankers.repository.CustomerRepository;
import com.jewelbankers.repository.ItemTypeRepository; // **New import for ItemTypeRepository**
import com.jewelbankers.repository.JewelDetailRepository;
import com.jewelbankers.repository.JewelRepository;
import com.jewelbankers.repository.SettingsRepository;

@Service
public class BillingService {

    @Autowired
    private JewelDetailRepository jewelDetailRepository;

    @Autowired
    private SettingsRepository settingsRepository;

    @Autowired
    private SettingsUtillity settingsUtility;
    
    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private JewelRepository jewelRepository;

    @Autowired
    private ItemTypeRepository itemTypeRepository;  // **Autowiring the ItemTypeRepository to fetch item types dynamically**
    
    
    public Page<Jewel> getAllJewelBills(int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "jewelsequence"));
		return jewelRepository.findAll(pageable);
	}
    
    public int getNextJewelNo() {
        // Logic to fetch the next available jewel number
        Integer currentJewelNo = jewelRepository.findCurrentJewelno();
        return (currentJewelNo == null) ? 1 : currentJewelNo + 1;
    }
    
    public Optional<Jewel> findById(Long id) {
        return jewelRepository.findById(id);
    }

    public void deleteJewelBill(Long id) {
    	jewelRepository.deleteById(id);
    }
    
    public JewelDetail addItemDetail(JewelDetail jewelDetail) {
        // Extract the fields from the JewelDetail object
        String barcode = jewelDetail.getBarcode();
        String itemdescription = jewelDetail.getItemdescription();
        int itemquantity = jewelDetail.getItemquantity();
        BigDecimal weight = jewelDetail.getJewel().getWeight();  // Retrieve weight from the Jewel object
        Integer itemtypeno = jewelDetail.getJewel().getItemtypeno();  // Get itemtypeno from Jewel object

        // Retrieve ItemType based on itemtypeno (from Jewel entity)
        ItemType itemType = itemTypeRepository.findById(itemtypeno)
            .orElseThrow(() -> new RuntimeException("Item Type not found"));

        // Create a new Jewel object and set the item type and weight
        Jewel jewel = new Jewel();
        jewel.setWeight(weight);  // Set the weight for the Jewel
        jewel.setItemtypeno(itemtypeno);  // Set itemtypeno in the Jewel entity

        // Set the Jewel object in JewelDetail (without saving)
        jewelDetail.setJewel(jewel);

        // Simply return the JewelDetail object without saving it
        return jewelDetail;
    }

    public Map<String, Object> getItemDetails(String barcode) {
        Map<String, Object> response = new HashMap<>();

        // Fetch JewelDetail by barcode
        Optional<JewelDetail> jewelDetailOptional = jewelDetailRepository.findByBarcode(barcode);
        if (!jewelDetailOptional.isPresent()) {
            response.put("error", "Item not found.");
            return response;
        }

        JewelDetail jewelDetail = jewelDetailOptional.get();

        // Convert jewelSequence from Integer to Long if needed
        Optional<Jewel> jewelOptional = jewelRepository.findById(Long.valueOf(jewelDetail.getJewel().getJewelsequence()));
        if (!jewelOptional.isPresent()) {
            response.put("error", "Jewel not found.");
            return response;
        }

        Jewel jewel = jewelOptional.get();
        
     // Fetch itemTypeCode based on itemTypeNo from ItemType repository
        Optional<ItemType> itemTypeOptional = itemTypeRepository.findByItemtypeno(jewel.getItemtypeno());
        if (!itemTypeOptional.isPresent()) {
            response.put("error", "Item type not found.");
            return response;
        }

        ItemType itemType = itemTypeOptional.get();

        // Populate the response with item details
        response.put("weight", jewel.getWeight()); // Assuming weight is in Jewel entity
        response.put("itemTypeCode", itemType.getItemtypecode()); 
        response.put("itemDescription", jewelDetail.getItemdescription()); // Assuming itemDescription is in JewelDetail entity

        return response;
    }
}