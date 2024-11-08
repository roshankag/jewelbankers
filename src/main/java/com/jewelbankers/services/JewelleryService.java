//package com.jewelbankers.services;
//
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.Base64;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.jpa.domain.Specification;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.jewelbankers.Utility.BillUtility;
//import com.jewelbankers.Utility.JewelleryUtility;
//import com.jewelbankers.Utility.SettingsUtillity;
//import com.jewelbankers.entity.Customer;
//import com.jewelbankers.entity.Jewellery;
//import com.jewelbankers.entity.Settings;
//import com.jewelbankers.repository.CustomerRepository;
//import com.jewelbankers.repository.JewelleryRepository;
//import com.jewelbankers.repository.SettingsRepository;
//
//import jakarta.persistence.EntityNotFoundException;
//import jakarta.persistence.criteria.CriteriaBuilder;
//import jakarta.persistence.criteria.CriteriaQuery;
//import jakarta.persistence.criteria.Predicate;
//import jakarta.persistence.criteria.Root;
//
//@Service
//public class JewelleryService {
//
//	@Autowired
//    private JewelleryRepository jewelleryRepository;
//
//    @Autowired
//    private CustomerRepository customerRepository;
//    
//    @Autowired
//    private SettingsUtillity settingsUtillity;
//    
//    @Autowired
//    private SettingsRepository settingsRepository;
//
//    @Transactional
//    public Jewellery saveJewellery(Jewellery jewellery) throws IOException {
//        // Ensure customer is saved before associating with jewellery
//        if (jewellery.getCustomer() != null) {
//            // Check if the customer already exists
//            if (jewellery.getCustomer().getCustomerid() != null) {
//                // Update existing customer details
//                Customer existingCustomer = getCustomer(jewellery);
//                jewellery.setCustomer(existingCustomer);
//            } else {
//                // If no ID exists, save the new customer
//                Customer savedCustomer = customerRepository.save(jewellery.getCustomer());
//                jewellery.setCustomer(savedCustomer);
//            }
//        }
//
//     // Fetch CGST and SGST from SettingsRepository using paramId
//        Optional<Settings> cgstSetting = settingsRepository.findByParamId("CGST");
//        Optional<Settings> sgstSetting = settingsRepository.findByParamId("SGST");
//
//        // Create a map for CGST and SGST only with required entries
//        Map<String, String> settingsMap = new HashMap<>();
//        
//        if (cgstSetting.isPresent()) {
//            settingsMap.put("CGST", cgstSetting.get().getParamValue());
//        } else {
//            throw new IllegalStateException("CGST setting not found");
//        }
//
//        if (sgstSetting.isPresent()) {
//            settingsMap.put("SGST", sgstSetting.get().getParamValue());
//        } else {
//            throw new IllegalStateException("SGST setting not found");
//        }
//
//        // Fetch CGST and SGST values using SettingsUtility methods
//        BigDecimal cgst = settingsUtillity.getCgst(settingsMap);
//        BigDecimal sgst = settingsUtillity.getSgst(settingsMap);
//
//        // Set CGST and SGST values in jewellery
//        jewellery.setCgst(cgst);
//        jewellery.setSgst(sgst);
//        
//        return jewelleryRepository.save(jewellery);
//    }
//
//    public Customer getCustomer(Jewellery jewellery) {
//        Optional<Customer> optionalCustomer = customerRepository.findById(jewellery.getCustomer().getCustomerid());
//
//        if (optionalCustomer.isPresent()) {
//            Customer existingCustomer = optionalCustomer.get();
//
//            // Update the existing customer's details from the incoming jewellery's customer
//            Customer incomingCustomer = jewellery.getCustomer();
//            existingCustomer.setAddress(incomingCustomer.getAddress());
//            existingCustomer.setPhoneno(incomingCustomer.getPhoneno());
//            existingCustomer.setMailid(incomingCustomer.getMailid());
//            existingCustomer.setProofType(incomingCustomer.getProofType());
//            existingCustomer.setProofDetails(incomingCustomer.getProofDetails());
//
//            // Save the updated customer
//            return customerRepository.save(existingCustomer);
//        }
//
//        return null; // Return null if customer not found
//    }
//    
//    
//    @Transactional
//    public Jewellery updateJewellery(Long invoiceSeq, Jewellery jewellery) {
//        // Fetch the existing jewellery from the repository
//        Optional<Jewellery> optionalJewellery = jewelleryRepository.findByInvoiceSeq(invoiceSeq);
//        
//        if (optionalJewellery.isPresent()) {
//            System.out.println("Jewellery Exists **********");
//            Jewellery existingJewellery = optionalJewellery.get();
//
//            // Update the fields from the provided jewellery object
//            existingJewellery.setItemDescription(jewellery.getItemDescription());
//            existingJewellery.setWeight(jewellery.getWeight());
//            existingJewellery.setPrize(jewellery.getPrize());
//            existingJewellery.setItemQuantity(jewellery.getItemQuantity());
//            existingJewellery.setInvoiceDate(jewellery.getInvoiceDate());
//            existingJewellery.setInvoiceNo(jewellery.getInvoiceNo());
//            existingJewellery.setItemRate(jewellery.getItemRate());
//            existingJewellery.setTotalPrize(jewellery.getTotalPrize());
//            existingJewellery.setCgst(jewellery.getCgst());
//            existingJewellery.setSgst(jewellery.getSgst());
//            
//            // Fetch the existing customer
//            Customer existingCustomer = existingJewellery.getCustomer();
//            
//            // Get the updated customer details
//            Customer updatedCustomer = jewellery.getCustomer();
//
//            // Update existing customer details only if the customer ID is present
//            if (updatedCustomer.getCustomerid() != null) {
//                existingCustomer.setCustomerName(updatedCustomer.getCustomerName());
//                existingCustomer.setPhoneno(updatedCustomer.getPhoneno());
//                existingCustomer.setAddress(updatedCustomer.getAddress());
//                existingCustomer.setProofType(updatedCustomer.getProofType());
//                existingCustomer.setProofDetails(updatedCustomer.getProofDetails());
//
//                // Update the existing customer in the repository
//                customerRepository.save(existingCustomer);
//            }
//
//            // Fetch CGST and SGST from SettingsRepository using paramId
//            Optional<Settings> cgstSetting = settingsRepository.findByParamId("CGST");
//            Optional<Settings> sgstSetting = settingsRepository.findByParamId("SGST");
//
//            // Create a map for CGST and SGST only with required entries
//            Map<String, String> settingsMap = new HashMap<>();
//
//            if (cgstSetting.isPresent()) {
//                settingsMap.put("CGST", cgstSetting.get().getParamValue());
//            } else {
//                throw new IllegalStateException("CGST setting not found");
//            }
//
//            if (sgstSetting.isPresent()) {
//                settingsMap.put("SGST", sgstSetting.get().getParamValue());
//            } else {
//                throw new IllegalStateException("SGST setting not found");
//            }
//
//            // Fetch CGST and SGST values using SettingsUtility methods
//            BigDecimal cgst = settingsUtillity.getCgst(settingsMap);
//            BigDecimal sgst = settingsUtillity.getSgst(settingsMap);
//
//            // Set CGST and SGST values in jewellery
//            existingJewellery.setCgst(cgst);
//            existingJewellery.setSgst(sgst);
//            
//            // Save and return the updated jewellery
//            return jewelleryRepository.save(existingJewellery);
//
//        } else {
//            // Handle case where the jewellery with the specified id does not exist
//            throw new EntityNotFoundException("Jewellery not found with id " + invoiceSeq);
//        }
//    }
//    
//    //Next invoice number 
//    public int getNextInvoiceNo() {
//        // Logic to fetch the next available invoice number
//        Integer currentInvoiceNo = jewelleryRepository.findCurrentInvoiceNo(); // You need to implement this in the repository
//        return (currentInvoiceNo == null) ? 1 : currentInvoiceNo + 1;
//    }
//    
// // Search functionality
//    public List<Jewellery> findJewelleryBySearch(String search, LocalDate fromDate, LocalDate toDate, String itemDescription, Double price, String sortOrder) {
//        try {
//            List<Jewellery> jewelleryBills = jewelleryRepository.findAll(new Specification<Jewellery>() {
//                @Override
//                public Predicate toPredicate(Root<Jewellery> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//                    List<Predicate> predicates = new ArrayList<>();
//                    Predicate searchPredicate;
//
//                    // Handle search by Invoice No or Customer Name
//                    if (search != null && !search.isEmpty()) {
//                        // Validate the invoice number format
//                        if (JewelleryUtility.ValidateInvoiceNo(search)) {
//                            Character invoiceSerial = search.toUpperCase().charAt(0);
//                            Integer invoiceNo = Integer.parseInt(search.substring(1));
//                            
//                            searchPredicate = cb.and(
//                                cb.equal(root.get("invoiceSerial"), invoiceSerial),
//                                cb.equal(root.get("invoiceNo"), invoiceNo)
//                            );
//                            predicates.add(searchPredicate);
//                        } else {
//                            // If not a valid invoice number, search by customer name
//                            searchPredicate = cb.like(root.get("customer").get("customerName"), "%" + search + "%");
//                            predicates.add(searchPredicate);
//                        }
//                    }
//                    
//                    // Handle date filtering
//                    if (fromDate != null && toDate != null) {
//                        predicates.add(cb.between(root.get("invoiceDate"), fromDate, toDate));
//                    } else if (fromDate != null) {
//                        predicates.add(cb.greaterThanOrEqualTo(root.get("invoiceDate"), fromDate));
//                    } else if (toDate != null) {
//                        predicates.add(cb.lessThanOrEqualTo(root.get("invoiceDate"), toDate));
//                    }
//                    
//                    // Handle item description filtering
//                    if (itemDescription != null && !itemDescription.isEmpty()) {
//                        predicates.add(cb.like(root.get("itemDescription"), "%" + itemDescription + "%"));
//                    }
//                    
//                    // Handle price filtering
//                    if (price != null) {
//                        predicates.add(cb.equal(root.get("price"), price));
//                    }
//                    
//                    // Apply the sorting
//                    if (sortOrder != null && sortOrder.equalsIgnoreCase("customername")) {
//                        query.orderBy(cb.asc(root.get("customer").get("customerName"))); // Ascending order
//                    } else {
//                        query.orderBy(cb.desc(root.get("invoiceSequence"))); // Default descending order
//                    }
//                    
//                    return cb.and(predicates.toArray(new Predicate[0]));
//                }
//            });
//
//            // Check if the result is empty and return an empty list if true
//            if (jewelleryBills.isEmpty()) {
//                System.out.println("No jewellery bills found for the given search criteria.");
//                return Collections.emptyList(); // Return an empty list to avoid 500 error
//            }
//
//            // Print product descriptions for each jewellery bill found
//            for (Jewellery jewellery : jewelleryBills) {
//                System.out.println("Invoice sequence: " + jewellery.getInvoiceSeq());
//                System.out.println("Product Description: " + jewellery.getItemDescription()); // Ensure you have this method
//            }
//            
//            return jewelleryBills;
//
//        } catch (Exception e) {
//            // Log the exception and return an empty list to prevent a 500 error
//            System.err.println("An error occurred while searching for jewellery bills: " + e.getMessage());
//            return Collections.emptyList();
//        }
//    }
//
//}
