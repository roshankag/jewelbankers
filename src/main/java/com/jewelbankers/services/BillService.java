package com.jewelbankers.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jewelbankers.Utility.BillUtility;
import com.jewelbankers.entity.Bill;
import com.jewelbankers.excel.ExcelGenerator;
import com.jewelbankers.repository.BillRepository;
import com.jewelbankers.repository.CustomerRepository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.Base64;

@Service
public class BillService {
	@Autowired
	private BillRepository billRepository;
	
	@Autowired
    private SettingsService settingsService;
	
	 @Autowired
	 private CustomerRepository customerRepository;
	
	public List<Bill> findBillsByProductTypeNo(Long productTypeNo) {
        return billRepository.findByProductTypeNo(productTypeNo);
    }
	
	public List<Bill> findBillsByRedemptionStatus(Character redemptionStatus) {
        return billRepository.findByRedemptionStatus(redemptionStatus);
    }
	
//	public List<Bill> searchBillsByDateRange(String startDate, String endDate) {
//        return billRepository.findByBillDateBetween(startDate, endDate);
//    }
//	
	public BillService(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    public ByteArrayInputStream exportBillsToExcel() throws IOException {
        List<Bill> bills = billRepository.findAll();
        return ExcelGenerator.generateBillExcel(bills);
    }
	
	public List<Bill> findBillsByCustomerName(String customerName, String street, Integer billNo) {
		return billRepository.findByCustomerCustomerNameOrCustomerStreetOrBillNo(customerName, street, billNo);
	}
	
	public List<Bill> findBillsBySearch(String search) {
		
		if (BillUtility.ValidateBillNo(search) ) {
			System.out.println("Bill"+search.charAt(0)+":"+Integer.parseInt(search.substring(1, search.length())));
			return billRepository.findByBillSerialAndBillNo(search.toUpperCase().charAt(0),Integer.parseInt(search.substring(1, search.length())));
		}else {
			return billRepository.findByCustomerCustomerName(search);
		}	
	}
	
	public List<Bill> findBillsBySearch(String search, LocalDate fromDate, LocalDate toDate, Integer amount, Character status, Integer productTypeNo) {
	    return billRepository.findAll(new Specification<Bill>() {
	        
	        @Override
	        public Predicate toPredicate(Root<Bill> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	            List<Predicate> predicates = new ArrayList<>();                
	            Predicate searchPredicate;
	            
	            // Handle search by Bill No or Customer Name
	            if (search != null && !search.isEmpty() && BillUtility.ValidateBillNo(search)) {
	                Character billSerial = search.toUpperCase().charAt(0);
	                Integer billNo = Integer.parseInt(search.substring(1));
	                
	                searchPredicate = cb.and(
	                    cb.equal(root.get("billSerial"), billSerial),
	                    cb.equal(root.get("billNo"), billNo)
	                );
	                predicates.add(searchPredicate);
	            } else if (search != null && !search.isEmpty()) {
	                searchPredicate = cb.like(root.get("customer").get("customerName"), "%" + search + "%");
	                predicates.add(searchPredicate);
	            }
	            
	            // Handle date filtering
	            if (fromDate != null && toDate != null) {
	                predicates.add(cb.between(root.get("billDate"), fromDate, toDate));
	            } else if (fromDate != null) {
	                predicates.add(cb.greaterThanOrEqualTo(root.get("billDate"), fromDate));
	            } else if (toDate != null) {
	                predicates.add(cb.lessThanOrEqualTo(root.get("billDate"), toDate));
	            }
	            
	            // Handle amount filtering
	            if (amount != null) {
	                predicates.add(cb.equal(root.get("amount"), amount));
	            }
	            
	            // Handle status filtering
	            if (status != null) {
	                predicates.add(cb.equal(root.get("redemptionStatus"), status));
	            }
	            
	            // Handle product type filtering
	            if (productTypeNo != null) {
	                predicates.add(cb.equal(root.get("productTypeNo"), productTypeNo));
	            }
	            
	            return cb.and(predicates.toArray(new Predicate[0]));
	        }
	    });
	}


	public List<Bill> findBillsByCustomerStreet(String street) {
		return billRepository.findByCustomerStreet(street);
	}


	public Page<Bill> getAllBills(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return billRepository.findAll(pageable);
	}

	public Optional<Bill> findById(Long billSequence) {
		return billRepository.findById(billSequence);
	}

//	public Bill saveBill(Bill bill) {
//		return billRepository.save(bill);
//	}
	
//	public Bill saveBill(Bill bill) {
//        // Check if the customer photo path is provided
//        if (bill.getCustomer() != null && bill.getCustomer().getImagePath() != null) {
//            String savedImagePath = saveCustomerPhoto(bill.getCustomer().getImagePath(), bill.getCustomer().getCustomerid());
//            bill.getCustomer().setImagePath(savedImagePath); // Update the image path in the customer entity
//            customerRepository.save(bill.getCustomer()); // Save the updated customer with the image path
//        }
//
//        // Save the bill to the database
//        return billRepository.save(bill);
//    }
//
//    private String saveCustomerPhoto(String base64Image, Long customerId) {
//        try {
//            // Get the directory path from the settings
//            String photoDir = settingsService.getCustomerPhotoDirectory();
//
//            // Ensure the directory exists
//            Path directoryPath = Paths.get(photoDir);
//            if (!Files.exists(directoryPath)) {
//                Files.createDirectories(directoryPath);
//            }
//
//            // Generate a unique filename based on customer ID
//            String fileName = "customer_" + customerId + ".jpg"; // or another appropriate extension
//            Path filePath = directoryPath.resolve(fileName);
//
//            // Decode the base64 image and save it to the file
//            byte[] imageBytes = Base64.getDecoder().decode(base64Image);
//            Files.write(filePath, imageBytes);
//
//            return filePath.toString(); // Return the path where the image is saved
//        } catch (IOException e) {
//            throw new RuntimeException("Error saving customer photo: " + e.getMessage(), e);
//        }
//    }
	
	// Method to save or update a bill without an image
    public Bill saveBill(Bill bill) {
        return billRepository.save(bill);
    }
	
    public Bill saveBill(Bill bill, MultipartFile photo) {
        if (photo != null && !photo.isEmpty()) {
            try {
                // Convert MultipartFile to Base64
                String base64Image = convertToBase64(photo);
                // Save the customer photo and get the path
                String savedImagePath = saveCustomerPhoto(base64Image, bill.getCustomer().getCustomerid());
                bill.getCustomer().setImagePath(savedImagePath); // Update the image path in the customer entity
                customerRepository.save(bill.getCustomer()); // Save the updated customer with the image path
            } catch (IOException e) {
                throw new RuntimeException("Error processing photo: " + e.getMessage(), e);
            }
        }

        // Save the bill to the database
        return billRepository.save(bill);
    }

    private String convertToBase64(MultipartFile file) throws IOException {
        byte[] fileBytes = file.getBytes();
        return Base64.getEncoder().encodeToString(fileBytes);
    }

    private String saveCustomerPhoto(String base64Image, Long customerId) {
        try {
            // Get the directory path from the settings
            String photoDir = settingsService.getCustomerPhotoDirectory();
            
            // Ensure the directory exists
            Path directoryPath = Paths.get(photoDir);
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }

            // Generate a unique filename based on customer name
            String safeCustomerId = customerId.toString().replaceAll("[^a-zA-Z0-9]", "_"); // Sanitize the customer name
            String uniqueFileName = "CustomerPhoto_" + safeCustomerId + "_" + System.currentTimeMillis() + ".jpg"; // Unique file name
            Path filePath = directoryPath.resolve(uniqueFileName);

            // Decode the base64 image and save it to the file
            byte[] imageBytes = Base64.getDecoder().decode(base64Image);
            Files.write(filePath, imageBytes);

            return filePath.toString(); // Return the path where the image is saved
        } catch (IOException e) {
            String errorMessage = "Error saving customer photo: " + e.getMessage();
            System.err.println(errorMessage);
            e.printStackTrace();
            throw new RuntimeException(errorMessage, e);
        }
    }
	
	public void deleteBill(Long id) {
		billRepository.deleteById(id);
	}
	
	public int getNextBillNo() {
        // Logic to fetch the next available bill number
        Integer currentBillNo = billRepository.findCurrentBillNo();
        return (currentBillNo == null) ? 1 : currentBillNo + 1;
    }
	public int getNextBillRedemNo() {
        // Logic to fetch the next available redeem number
        Integer currentBillNo = billRepository. findCurrentBillRedemNo();
        return (currentBillNo == null) ? 1 : currentBillNo + 1;
    }
	
	public List<Bill> findBillsByBillNo(Character billSerial,Integer billNo, Long billSequence ) 
	  { 
		  if(billNo != null && billNo >0) return billRepository.findByBillSerialAndBillNo(billSerial,billNo);
		  else return billRepository.findByBillSequence(billSequence);
	  
	  }
	 
}
