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
import com.jewelbankers.entity.Customer;
import com.jewelbankers.excel.ExcelGenerator;
import com.jewelbankers.repository.BillRepository;
import com.jewelbankers.repository.CustomerRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

import java.util.Base64;
import java.time.temporal.ChronoUnit;
import com.jewelbankers.entity.Settings; // Adjust the package name based on your project structure


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

	// Method to save or update a bill without an image
    public Bill saveBill(Bill bill) {
        return billRepository.save(bill);
    }
    
    @Transactional
    public Bill saveBill(Bill bill, MultipartFile photo) {
        if (photo != null && !photo.isEmpty()) {
            try {
                // Convert MultipartFile to Base64
                String base64Image = convertToBase64(photo);
                
                // Save the customer photo and get the path
                String savedImagePath = saveCustomerPhoto(base64Image, bill.getCustomer().getCustomerid());
                
                bill.getCustomer().setImagePath(savedImagePath); // Update the image path in the customer entity
                
             // Fetch the existing customer and update its image path
                Customer customer = customerRepository.findById(bill.getCustomer().getCustomerid())
                        .orElseThrow(() -> new RuntimeException("Customer not found"));
                customer.setImagePath(savedImagePath); // Update the image path
                customerRepository.save(customer); // Save the updated customer

                // Attach the updated customer to the bill
                bill.setCustomer(customer);
                
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
	
	public Bill updateBill(Long id, Bill billDetails) {
        Optional<Bill> billOptional = findById(id);
        if (!billOptional.isPresent()) {
            throw new EntityNotFoundException("Bill with id " + id + " not found");
        }

        Bill existingBill = billOptional.get();
        
        if (billDetails.getBillSerial() != null) existingBill.setBillSerial(billDetails.getBillSerial());
        if (billDetails.getBillNo() != null) existingBill.setBillNo(billDetails.getBillNo());
        if (billDetails.getBillDate() != null) existingBill.setBillDate(billDetails.getBillDate());
        if (billDetails.getCustomer() != null) existingBill.setCustomer(billDetails.getCustomer());
        if (billDetails.getCareOf() != null) existingBill.setCareOf(billDetails.getCareOf());
        if (billDetails.getProductTypeNo() != null) existingBill.setProductTypeNo(billDetails.getProductTypeNo());
        if (billDetails.getRateOfInterest() != null) existingBill.setRateOfInterest(billDetails.getRateOfInterest());
        if (billDetails.getAmount() != null) existingBill.setAmount(billDetails.getAmount());
        if (billDetails.getAmountInWords() != null) existingBill.setAmountInWords(billDetails.getAmountInWords());
        if (billDetails.getPresentValue() != null) existingBill.setPresentValue(billDetails.getPresentValue());
        if (billDetails.getGrams() != null) existingBill.setGrams(billDetails.getGrams());
        if (billDetails.getMonthlyIncome() != null) existingBill.setMonthlyIncome(billDetails.getMonthlyIncome());
        if (billDetails.getRedemptionDate() != null) existingBill.setRedemptionDate(billDetails.getRedemptionDate());
        if (billDetails.getRedemptionInterest() != null) existingBill.setRedemptionInterest(billDetails.getRedemptionInterest());
        if (billDetails.getRedemptionTotal() != null) existingBill.setRedemptionTotal(billDetails.getRedemptionTotal());
        if (billDetails.getRedemptionStatus() != null) existingBill.setRedemptionStatus(billDetails.getRedemptionStatus());
        if (billDetails.getBillRedemSerial() != null) existingBill.setBillRedemSerial(billDetails.getBillRedemSerial());
        if (billDetails.getBillRedemNo() != null) existingBill.setBillRedemNo(billDetails.getBillRedemNo());
        if (billDetails.getComments() != null) existingBill.setComments(billDetails.getComments());

        // Calculate redemption interest and total
        double interestRate = getRateOfInterest(existingBill.getProductTypeNo(), existingBill.getAmount());
        long daysBetween = ChronoUnit.DAYS.between(existingBill.getBillDate(), 
                           existingBill.getRedemptionDate() != null ? existingBill.getRedemptionDate() : LocalDate.now());

        double redemptionInterest = (existingBill.getAmount() * interestRate * daysBetween) / 36500;
        double redemptionTotal = existingBill.getAmount() + redemptionInterest;

        existingBill.setRedemptionInterest(redemptionInterest);
        existingBill.setRedemptionTotal(redemptionTotal);

        return saveBill(existingBill);
    }

	private double getRateOfInterest(Integer productTypeNo, double amount) {
	    // Convert productTypeNo to string and compare with the expected product types
	    if ("GOLD".equalsIgnoreCase(productTypeNo.toString())) {
	        // Determine the interest rate based on the amount for GOLD
	        if (amount < 5000) return getSettingValue(44L); // GOLD_INTREST_LESS_THAN_5000
	        if (amount < 10000) return getSettingValue(45L); // GOLD_INTREST_LESS_THAN_10000
	        if (amount < 20000) return getSettingValue(46L); // GOLD_INTREST_LESS_THAN_20000
	        if (amount < 50000) return getSettingValue(47L); // GOLD_INTREST_LESS_THAN_50000
	        if (amount < 100000) return getSettingValue(48L); // GOLD_INTREST_LESS_THAN_100000
	        return getSettingValue(49L); // GOLD_INTREST_MORE_THAN_100000
	    } else if ("SILVER".equalsIgnoreCase(productTypeNo.toString())) {
	        // Default interest rate for SILVER
	        return getSettingValue(50L); // SILVER_INTREST
	    }
	    
	    // Throw an exception if the product type is invalid
	    throw new IllegalArgumentException("Invalid product type: " + productTypeNo);
	}

    private double getSettingValue(Long paramSeq) {
        // Retrieve the Optional<Settings> from the service
        Optional<Settings> optionalSetting = settingsService.findByParamSeq(paramSeq);
        
        // Extract the Settings object if present, or return a default value
        Settings setting = optionalSetting.orElse(null);
        
        // If setting is not null, parse the paramValue; otherwise, return 0.0
        return setting != null ? Double.parseDouble(setting.getParamValue()) : 0.0;
    }
	
}
