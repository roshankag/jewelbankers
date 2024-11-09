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

        // Save the Jewel entity first (if required)
        jewelRepository.save(jewel);

        // Set the Jewel object in JewelDetail
        jewelDetail.setJewel(jewel);

        // Save JewelDetail
        jewelDetailRepository.save(jewelDetail);

        return jewelDetail;  // Return the created JewelDetail object
    }
    
    @Transactional
    public Jewel createJewelBill(String barcode, Long customerid, BigDecimal makingChargePercent, 
                                 BigDecimal wastageChargePercent, MultipartFile photo) throws IOException {

        // Fetch item details based on barcode
        JewelDetail jewelDetail = jewelDetailRepository.findByBarcode(barcode)
                .orElseThrow(() -> new RuntimeException("Item not found for the given barcode"));

        // Convert settings to map
        List<Settings> settingsList = settingsRepository.findAll();
        Map<String, String> settingsMap = settingsUtility.convertListToMap(settingsList);

        // Fetch rate based on itemtypeno in Jewel entity
        Jewel jewel = new Jewel();
        BigDecimal rate = settingsUtility.getRateByItemType(settingsMap, jewel.getItemtypeno());

        // Calculate total amount, making charge, and wastage charge
        BigDecimal weight = jewel.getWeight();
        BigDecimal makingCharge = rate.multiply(weight).multiply(makingChargePercent).divide(new BigDecimal("100"));
        BigDecimal wastageCharge = rate.multiply(weight).multiply(wastageChargePercent).divide(new BigDecimal("100"));
        BigDecimal totalAmount = rate.multiply(weight).add(makingCharge).add(wastageCharge);

        // Set customer details
        Customer customer = customerRepository.findById(customerid)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        if (photo != null && !photo.isEmpty()) {
            // Save the photo as a byte array in the Customer entity
            customer.setPhoto(photo.getBytes());
        }

        // Set customer to jewel
        jewel.setCustomer(customer);  // Associate the customer with the jewel

        // Set values in the Jewel entity
        jewel.setTotalamount(totalAmount.intValue());
        jewel.setMakingcharge(makingCharge);
        jewel.setWastagecharge(wastageCharge);
        jewel.setWeight(weight);
        jewel.setItemtypeno(jewel.getItemtypeno()); // Assuming itemtypeno is already set

        // Save the customer (if new or modified)
        customerRepository.save(customer);

        // Save the Jewel entity
        return jewelRepository.save(jewel);
    }


    /**
     * Fetch item details from the database based on barcode and calculate
     * total amount, making charge, and wastage charge for gold or silver.
     * This method also auto-populates item information such as item type, 
     * quantity, description, and weight from the Jewel entity.
     * 
     * @param barcode Barcode of the item.
     * @param makingChargePercentage Making charge percentage provided by the client.
     * @param wastageChargePercentage Wastage charge percentage provided by the client.
     * @return Map containing calculated results and item details.
     */
    public Map<String, Object> getItemDetailsAndCalculate(String barcode, BigDecimal makingChargePercentage, BigDecimal wastageChargePercentage) {
        Map<String, Object> response = new HashMap<>();

        // Fetch item details from the database using barcode
        Optional<JewelDetail> itemOpt = jewelDetailRepository.findByBarcode(barcode);
        if (itemOpt.isPresent()) {
            JewelDetail itemDetail = itemOpt.get();
            
            // Get Jewel entity for weight and other details
            Jewel jewel = itemDetail.getJewel(); // Assuming JewelDetail has a reference to Jewel
            
            // Fetch item type dynamically from itemNo (new change to avoid hardcoding)
            String itemType = getItemTypeByItemNo(jewel.getItemtypeno()); // **Updated to get item type dynamically**

            // Auto-populate item details
            //response.put("itemno", itemDetail.getItemno());
            response.put("itemTypeNo", jewel.getItemtypeno()); // **Updated itemType assignment from dynamic method**
            response.put("quantity", itemDetail.getItemquantity());
            response.put("itemDescription", itemDetail.getItemdescription());
            response.put("weight", jewel.getWeight()); // Now accessing weight from Jewel entity
            
            
//         // Fetch customer details if available
//            if (jewel.getCustomer() != null) {
//                Customer customer = jewel.getCustomer();
//                
//                // Add customer details to the response
//                response.put("customerName", customer.getCustomerName());
//                response.put("address", customer.getAddress());
//                response.put("phoneNo", customer.getPhoneno());
//                response.put("proofType", customer.getProofType());
//                response.put("proofDetails", customer.getProofDetails());
//                response.put("photo", customer.getPhoto());
//            } else {
//                response.put("error", "Customer details not found for the given item");
//            }

         // Fetch settings and convert to a map
            List<Settings> settingsList = settingsRepository.findAll();
            Map<String, String> settingsMap = settingsUtility.convertListToMap(settingsList);

            // Determine the correct rate based on itemtypeno from jewel entity
            BigDecimal rate = settingsUtility.getRateByItemType(settingsMap, jewel.getItemtypeno());

            // Calculate the base amount based on weight and item type
            BigDecimal baseAmount = rate.multiply(jewel.getWeight());

            // Calculate making charge and wastage charge based on client input
            BigDecimal makingCharge = baseAmount.multiply(makingChargePercentage).divide(BigDecimal.valueOf(100));
            BigDecimal wastageCharge = baseAmount.multiply(wastageChargePercentage).divide(BigDecimal.valueOf(100));

            // Calculate the final total amount including making and wastage charges
            BigDecimal totalAmount = baseAmount.add(makingCharge).add(wastageCharge);

            // Populate the response with the combined total amount
            response.put("rate", rate);
            response.put("makingcharges", makingCharge);
            response.put("wastagecharges", wastageCharge);
            response.put("totalAmount", totalAmount);

        } else {
            // Handle item not found scenario
            response.put("error", "Item not found for the given barcode");
        }

        return response;
    }

    /**
     * Calculate the total amount for an item based on its type (gold or silver)
     * and its weight. It fetches the correct rate for gold or silver based on
     * the item type.
     * 
     * @param itemDetail JewelDetail object containing item details.
     * @param goldRate The gold rate fetched from settings.
     * @param silverRate The silver rate fetched from settings.
     * @param weight The weight of the item (from Jewel entity).
     * @param itemType The item type (gold/silver) to determine the rate.
     * @return The calculated total amount based on item type and weight.
     */
    private BigDecimal calculateTotalAmount(JewelDetail itemDetail, BigDecimal goldRate, BigDecimal silverRate, BigDecimal diamondRate, BigDecimal weight, String itemType) {
        BigDecimal totalAmount = BigDecimal.ZERO;

        // Check item type and calculate total amount accordingly
        if ("GOLD".equalsIgnoreCase(itemType)) {  // **Condition for itemType GOLD**
            // If item type is gold, use gold rate
            totalAmount = goldRate.multiply(weight);  // Example: totalAmount = goldRate * weight
        } else if ("SILVER".equalsIgnoreCase(itemType)) {  // **Condition for itemType SILVER**
            // If item type is silver, use silver rate
            totalAmount = silverRate.multiply(weight);  // Example: totalAmount = silverRate * weight
        } else if ("DIAMOND".equalsIgnoreCase(itemType)) {  // **New condition for itemType DIAMOND**
            // If item type is diamond, use diamond rate
            totalAmount = diamondRate.multiply(weight);  // Example: totalAmount = diamondRate * weight
        } else {
            // Invalid item type (not gold, silver, or diamond) - handle gracefully
            totalAmount = BigDecimal.ZERO; // Set to zero or an appropriate value
            System.out.println("Invalid item type: " + itemType);  // Log the invalid item type for debugging
        }

        return totalAmount;
    }


    /**
     * Retrieve the item type dynamically based on item number.
     * 
     * @param itemNo The item number to fetch the item type.
     * @return The item type as a string ("GOLD", "SILVER", etc.).
     */
    private String getItemTypeByItemNo(int itemNo) {
        // **New method to fetch item type dynamically using the itemNo**
        switch (itemNo) {
            case 1:
                return "GOLD";
            case 2:
                return "SILVER";
            case 3:
                return "DIAMOND";
            default:
                return "UNKNOWN"; // Default if itemNo is not recognized
        }
    }
 
    public void updateCustomerDetails(Long customerId, String customerName, String address, Long phoneNo, Character proofType, String proofDetails, String photo) {
        Optional<Customer> customerOpt = customerRepository.findById(customerId);

        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();

            // Update customer details
            customer.setCustomerName(customerName);
            customer.setAddress(address);
            customer.setPhoneno(phoneNo);
            customer.setProofType(proofType);
            customer.setProofDetails(proofDetails);

            // Convert photo from String (Base64) to byte[]
            if (photo != null && !photo.isEmpty()) {
                byte[] photoBytes = Base64.getDecoder().decode(photo);
                customer.setPhoto(photoBytes);
            }

            // Save updated customer back to the database
            customerRepository.save(customer);
        } else {
            // Handle case where customer does not exist
            throw new RuntimeException("Customer with ID " + customerId + " not found");
        }
    }

    
    public void addNewCustomer(String barcode, String customerName, String address, Long phoneNo, Character proofType, String proofDetails, String photo) {
        Optional<JewelDetail> itemOpt = jewelDetailRepository.findByBarcode(barcode);

        if (itemOpt.isPresent()) {
            JewelDetail itemDetail = itemOpt.get();
            
            // Create new customer
            Customer newCustomer = new Customer();
            newCustomer.setCustomerName(customerName);
            newCustomer.setAddress(address);
            newCustomer.setPhoneno(phoneNo);
            newCustomer.setProofType(proofType);
            newCustomer.setProofDetails(proofDetails);

            // Convert photo from String (Base64) to byte[]
            if (photo != null && !photo.isEmpty()) {
                byte[] photoBytes = Base64.getDecoder().decode(photo);
                newCustomer.setPhoto(photoBytes);
            }

            // Save the new customer
            customerRepository.save(newCustomer);

            // Associate the customer with the jewel (through JewelDetail's Jewel entity)
            Jewel jewel = itemDetail.getJewel(); // Assuming JewelDetail has a reference to Jewel
            jewel.setCustomer(newCustomer); // Assuming Jewel has a setCustomer method
            jewelRepository.save(jewel);    // Save Jewel with the new customer association

        } else {
            // Handle case where item is not found
            throw new RuntimeException("Item not found for the given barcode");
        }
    }

}
