package com.jewelbankers.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.itextpdf.text.DocumentException;
import com.jewelbankers.Utility.BillUtility;
import com.jewelbankers.Utility.ProductTypeUtility;
import com.jewelbankers.Utility.SettingsUtillity;
import com.jewelbankers.entity.Bill;
import com.jewelbankers.entity.Customer;
import com.jewelbankers.excel.ExcelGenerator;
import com.jewelbankers.repository.BillRepository;
import com.jewelbankers.repository.CustomerRepository;
import com.jewelbankers.repository.SettingsRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;


@Service
public class BillService {
	@Autowired
	private BillRepository billRepository;
	
	@Autowired
    private SettingsService settingsService;
	
	@Autowired
    private SettingsRepository settingsRepository;
	
	 @Autowired
	 private CustomerRepository customerRepository;
	 
	 @Autowired
	 private EntityManager entityManager;
	 
	 @Autowired
	 private CustomerPdfService customerPdfService;
	 
	 @Autowired
	 private OfficePdfService officePdfService;
	
	 @Autowired
	 SettingsUtillity settingsUtillity; 
	 
	 @Autowired
	 ProductTypeUtility productTypeUtility;
	 
	 @Autowired
	 private PdfRedeemService pdfRedeemService;
	 
	 
	public List<Bill> findBillsByProductTypeNo(Long productTypeNo) {
        return billRepository.findByProductTypeNo(productTypeNo);
    }
	
	public List<Bill> findBillsByRedemptionStatus(Character redemptionStatus) {
        return billRepository.findByRedemptionStatus(redemptionStatus);
    }
	
	public BillService(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

//    public ByteArrayInputStream exportBillsToExcel() throws IOException {
//        List<Bill> bills = billRepository.findAll();
//        return ExcelGenerator.generateBillExcel(bills);
//    }
    
	public ByteArrayInputStream exportBillsToExcel(String search, LocalDate fromDate, LocalDate toDate, Integer amount, Character status, Integer productTypeNo, String sortOrder) throws IOException {
	    // Retrieve the list of bills based on the search criteria
	    List<Bill> bills = findBillsBySearch(search, fromDate, toDate, amount, status, productTypeNo, sortOrder);
	    
	    // Generate the Excel file from the list of bills
	    return ExcelGenerator.generateBillExcel(bills);
	}

	
	public List<Bill> findBillsByCustomerName(String customerName, String street, Integer billNo) {
		return billRepository.findByCustomerCustomerNameOrCustomerStreetOrBillNo(customerName, street, billNo);
	}
	
//	public List<Bill> findBillsBySearch(String search) {
//		
//		if (BillUtility.ValidateBillNo(search) ) {
//			System.out.println("Bill"+search.charAt(0)+":"+Integer.parseInt(search.substring(1, search.length())));
//			return billRepository.findByBillSerialAndBillNo(search.toUpperCase().charAt(0),Integer.parseInt(search.substring(1, search.length())));
//		}else {
//			return billRepository.findByCustomerCustomerName(search);
//		}	
//	}
	
	public List<Bill> findBillsBySearch(String search) {
	    if (BillUtility.ValidateBillNo(search)) {
	        System.out.println("Bill" + search.charAt(0) + ":" + Integer.parseInt(search.substring(1)));
	        // Returning bills sorted by 'billSeq' in descending order
	        return billRepository.findByBillSerialAndBillNoOrderByBillSequenceDesc(
	            search.toUpperCase().charAt(0), 
	            Integer.parseInt(search.substring(1))
	        );
	    } else {
	        // Returning bills sorted by 'billSeq' in descending order
	        return billRepository.findByCustomerCustomerNameOrderByBillSequenceDesc(search);
	    }    
	}
	 public List<Bill> findBillsBySearch(String search, LocalDate fromDate, LocalDate toDate, Integer amount, Character status, Integer productTypeNo, String sortOrder) {
	        try {
	            List<Bill> bills = billRepository.findAll(new Specification<Bill>() {
	                
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
	                    
	                    // Apply the sorting
	                    if(sortOrder != null && sortOrder.equalsIgnoreCase("customername")) {
	                        query.orderBy(cb.desc(root.get("customer").get("customerName")));
	                    } else {
	                        query.orderBy(cb.desc(root.get("billSequence")));
	                    }
	                    
	                    return cb.and(predicates.toArray(new Predicate[0]));
	                }
	            });

	            // **Check if the result is empty and return an empty list if true**
	            if (bills.isEmpty()) {
	                System.out.println("No bills found for the given search criteria.");
	                return Collections.emptyList(); // **Return an empty list to avoid 500 error**
	            }

	            return bills;

	        } catch (Exception e) {
	            // **Log the exception and return an empty list to prevent a 500 error**
	            System.err.println("An error occurred while searching for bills: " + e.getMessage());
	            return Collections.emptyList();
	        }
	    }


	public List<Bill> findBillsByCustomerStreet(String street) {
		return billRepository.findByCustomerStreet(street);
	}


	public Page<Bill> getAllBills(int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "billSequence"));
		return billRepository.findAll(pageable);
	}
	
	public Optional<Bill> findById(Long billSequence) {
	    Map<String, String> settingsMap = getSettingMap();
	    Optional<Bill> optionalBill = billRepository.findById(billSequence);
	    // If the Bill is present, calculateRedemption is called with settingsMap
	    return optionalBill.map(bill -> calculateRedemption(bill, settingsMap));
	}

	// Method to save or update a bill without an image
	@Transactional
	public Bill saveBill(Bill bill) {
		if(bill.getCustomer().getCustomerid() != null) {
			
	            // Set the updated customer back to the bill
	            bill.setCustomer(getCustomer(bill));
	        }
		    
		   // optionalCustomer.ifPresentOrElse(customer -> bill.setCustomer(customer), null);
	    // If the customer is detached, merge it to make it managed
		/*
		 * if (bill.getCustomer() != null && entityManager.contains(bill.getCustomer())
		 * == false) { Customer managedCustomer =
		 * entityManager.merge(bill.getCustomer()); bill.setCustomer(managedCustomer); }
		 */
	
	    return billRepository.save(bill);
	}
    public Customer getCustomer(Bill bill) 
    {
    	Optional<Customer> optionalCustomer =  customerRepository.findById(bill.getCustomer().getCustomerid());
	    //optionalCustomer.ifPresent(customer -> bill.setCustomer(customer));
	    
	    if (optionalCustomer.isPresent()) {
            Customer existingCustomer = optionalCustomer.get();
            
            // Update the existing customer's details from the incoming bill's customer
            Customer incomingCustomer = bill.getCustomer();
            existingCustomer.setAddress(incomingCustomer.getAddress());
            existingCustomer.setPhoneno(incomingCustomer.getPhoneno());
            existingCustomer.setMailid(incomingCustomer.getMailid());
            existingCustomer.setProofType(incomingCustomer.getProofType());
            existingCustomer.setProofDetails(incomingCustomer.getProofDetails());

            // Save the updated customer
            return customerRepository.save(existingCustomer);
         //   return existingCustomer;
	    }
	    return null;
    }
	// Example method to get shop details and include in the bill
    public Map<String, String> getShopDetailsForBill() {
        return settingsService.getShopDetails();
    }
	/*
	 * @Transactional public Bill saveBill(Bill bill, MultipartFile photo) { if
	 * (photo != null && !photo.isEmpty()) { try { // Convert MultipartFile to
	 * Base64 String base64Image = convertToBase64(photo);
	 * 
	 * // Save the customer photo and get the path String savedImagePath =
	 * saveCustomerPhoto(base64Image, bill.getCustomer().getCustomerid());
	 * 
	 * bill.getCustomer().setImagePath(savedImagePath); // Update the image path in
	 * the customer entity
	 * 
	 * // Fetch the existing customer and update its image path Customer customer =
	 * customerRepository.findById(bill.getCustomer().getCustomerid())
	 * .orElseThrow(() -> new RuntimeException("Customer not found"));
	 * customer.setImagePath(savedImagePath); // Update the image path
	 * customerRepository.save(customer); // Save the updated customer
	 * 
	 * // Attach the updated customer to the bill bill.setCustomer(customer);
	 * 
	 * } catch (IOException e) { e.printStackTrace(); throw new
	 * RuntimeException("Error processing photo: " + e.getMessage(), e); } }
	 * 
	 * // Save the bill to the database return billRepository.save(bill); }
	 * 
	 * private String convertToBase64(MultipartFile file) throws IOException {
	 * byte[] fileBytes = file.getBytes(); return
	 * Base64.getEncoder().encodeToString(fileBytes); }
	 */

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
//            // Generate a unique filename based on customer name
//            String safeCustomerId = customerId.toString().replaceAll("[^a-zA-Z0-9]", "_"); // Sanitize the customer name
//            String uniqueFileName = "CustomerPhoto_" + safeCustomerId + "_" + System.currentTimeMillis() + ".jpg"; // Unique file name
//            Path filePath = directoryPath.resolve(uniqueFileName);
//
//            // Decode the base64 image and save it to the file
//            byte[] imageBytes = Base64.getDecoder().decode(base64Image);
//            Files.write(filePath, imageBytes);
//
//            return filePath.toString(); // Return the path where the image is saved
//        } catch (IOException e) {
//            String errorMessage = "Error saving customer photo: " + e.getMessage();
//            System.err.println(errorMessage);
//            e.printStackTrace();
//            throw new RuntimeException(errorMessage, e);
//        }
//    }
	
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
		 Map<String, String> settingsMap = getSettingMap();
		  if(billNo != null && billNo >0) {
			  List<Bill> bills = billRepository.findByBillSerialAndBillNo(billSerial,billNo);
			  for (Bill bill2 : bills) {
				  int monthsbetween= monthsBetween(bill2);
				bill2.setInterestinmonths(monthsbetween);
				bill2.setRedemptionInterest(redemptioninterest(monthsbetween, bill2.getAmount(),settingsMap));
				bill2.setReceivedinterest(
						getReceievedInterest(
								new BigDecimal(bill2.getAmount()), monthsbetween, 
								getRateOfInterest(bill2.getProductTypeNo().intValue(), bill2.getAmount().intValue(),settingsMap)).doubleValue());

				
			}
			  return bills;
		  }
		  
		  else return billRepository.findByBillSequence(billSequence);
	  
	  }
	
//	public Bill updateBill(Long id, Bill billDetails) {
//        Optional<Bill> billOptional = findById(id);
//        if (!billOptional.isPresent()) {
//            throw new EntityNotFoundException("Bill with id " + id + " not found");
//        }
//
//        Bill existingBill = billOptional.get();
//        
//        if (billDetails.getBillSerial() != null) existingBill.setBillSerial(billDetails.getBillSerial());
//        if (billDetails.getBillNo() != null) existingBill.setBillNo(billDetails.getBillNo());
//        if (billDetails.getBillDate() != null) existingBill.setBillDate(billDetails.getBillDate());
//        if (billDetails.getCustomer() != null) existingBill.setCustomer(billDetails.getCustomer());
//        if (billDetails.getCareOf() != null) existingBill.setCareOf(billDetails.getCareOf());
//        if (billDetails.getProductTypeNo() != null) existingBill.setProductTypeNo(billDetails.getProductTypeNo());
//        if (billDetails.getRateOfInterest() != null) existingBill.setRateOfInterest(billDetails.getRateOfInterest());
//        if (billDetails.getAmount() != null) existingBill.setAmount(billDetails.getAmount());
//        if (billDetails.getAmountInWords() != null) existingBill.setAmountInWords(billDetails.getAmountInWords());
//        if (billDetails.getPresentValue() != null) existingBill.setPresentValue(billDetails.getPresentValue());
//        if (billDetails.getGrams() != null) existingBill.setGrams(billDetails.getGrams());
//        if (billDetails.getMonthlyIncome() != null) existingBill.setMonthlyIncome(billDetails.getMonthlyIncome());
//        if (billDetails.getRedemptionDate() != null) existingBill.setRedemptionDate(billDetails.getRedemptionDate());
//        if (billDetails.getRedemptionInterest() != null) existingBill.setRedemptionInterest(billDetails.getRedemptionInterest());
//        if (billDetails.getRedemptionTotal() != null) existingBill.setRedemptionTotal(billDetails.getRedemptionTotal());
//        if (billDetails.getRedemptionStatus() != null) existingBill.setRedemptionStatus(billDetails.getRedemptionStatus());
//        if (billDetails.getBillRedemSerial() != null) existingBill.setBillRedemSerial(billDetails.getBillRedemSerial());
//        if (billDetails.getBillRedemNo() != null) existingBill.setBillRedemNo(billDetails.getBillRedemNo());
//        if (billDetails.getComments() != null) existingBill.setComments(billDetails.getComments());
//
//        // Calculate redemption interest and total
//        double interestRate = getRateOfInterest(existingBill.getProductTypeNo(), existingBill.getAmount());
//        long daysBetween = ChronoUnit.DAYS.between(existingBill.getBillDate(), 
//                           existingBill.getRedemptionDate() != null ? existingBill.getRedemptionDate() : LocalDate.now());
//
//        double redemptionInterest = (existingBill.getAmount() * interestRate * daysBetween) / 36500;
//        double redemptionTotal = existingBill.getAmount() + redemptionInterest;
//
//        existingBill.setRedemptionInterest(redemptionInterest);
//        existingBill.setRedemptionTotal(redemptionTotal);
//
//        return saveBill(existingBill);
//    }
//
//	private double getRateOfInterest(Integer productTypeNo, double amount) {
//	    // Convert productTypeNo to string and compare with the expected product types
//	    if ("GOLD".equalsIgnoreCase(productTypeNo.toString())) {
//	        // Determine the interest rate based on the amount for GOLD
//	        if (amount < 5000) return getSettingValue(44L); // GOLD_INTREST_LESS_THAN_5000
//	        if (amount < 10000) return getSettingValue(45L); // GOLD_INTREST_LESS_THAN_10000
//	        if (amount < 20000) return getSettingValue(46L); // GOLD_INTREST_LESS_THAN_20000
//	        if (amount < 50000) return getSettingValue(47L); // GOLD_INTREST_LESS_THAN_50000
//	        if (amount < 100000) return getSettingValue(48L); // GOLD_INTREST_LESS_THAN_100000
//	        return getSettingValue(49L); // GOLD_INTREST_MORE_THAN_100000
//	    } else if ("SILVER".equalsIgnoreCase(productTypeNo.toString())) {
//	        // Default interest rate for SILVER
//	        return getSettingValue(50L); // SILVER_INTREST
//	    }
//	    
//	    // Throw an exception if the product type is invalid
//	    throw new IllegalArgumentException("Invalid product type: " + productTypeNo);
//	}
//
//    private double getSettingValue(Long paramSeq) {
//        // Retrieve the Optional<Settings> from the service
//        Optional<Settings> optionalSetting = settingsService.findByParamSeq(paramSeq);
//        
//        // Extract the Settings object if present, or return a default value
//        Settings setting = optionalSetting.orElse(null);
//        
//        // If setting is not null, parse the paramValue; otherwise, return 0.0
//        return setting != null ? Double.parseDouble(setting.getParamValue()) : 0.0;
//    }
//	
//	public Bill updateBill(Long id, Bill billDetails) {
//        Optional<Bill> billOptional = billRepository.findById(id);
//        if (!billOptional.isPresent()) {
//            throw new EntityNotFoundException("Bill with id " + id + " not found");
//        }
//
//        Bill existingBill = billOptional.get();
//
//        // Update fields with provided billDetails
//        if (billDetails.getBillSerial() != null) existingBill.setBillSerial(billDetails.getBillSerial());
//        if (billDetails.getBillNo() != null) existingBill.setBillNo(billDetails.getBillNo());
//        if (billDetails.getBillDate() != null) existingBill.setBillDate(billDetails.getBillDate());
//        
//     // Handle the customer entity
//        if (billDetails.getCustomer() != null) {
//            Customer customer = billDetails.getCustomer();
//            if (customer.getCustomerid() != null) {
//                // Retrieve the existing customer from the database
//                Optional<Customer> customerOptional = customerRepository.findById(customer.getCustomerid());
//                if (customerOptional.isPresent()) {
//                    existingBill.setCustomer(customerOptional.get());
//                } else {
//                    throw new EntityNotFoundException("Customer with id " + customer.getCustomerid() + " not found");
//                }
//            } else {
//                // Optionally handle the case where no ID is provided
//                throw new IllegalArgumentException("Customer ID cannot be null");
//            }
//        }
//        
//        
//        if (billDetails.getCareOf() != null) existingBill.setCareOf(billDetails.getCareOf());
//        if (billDetails.getProductTypeNo() != null) existingBill.setProductTypeNo(billDetails.getProductTypeNo());
//        if (billDetails.getRateOfInterest() != null) existingBill.setRateOfInterest(billDetails.getRateOfInterest());
//        if (billDetails.getAmount() != null) existingBill.setAmount(billDetails.getAmount());
//        if (billDetails.getAmountInWords() != null) existingBill.setAmountInWords(billDetails.getAmountInWords());
//        if (billDetails.getPresentValue() != null) existingBill.setPresentValue(billDetails.getPresentValue());
//        if (billDetails.getGrams() != null) existingBill.setGrams(billDetails.getGrams());
//        if (billDetails.getMonthlyIncome() != null) existingBill.setMonthlyIncome(billDetails.getMonthlyIncome());
//        if (billDetails.getRedemptionDate() != null) existingBill.setRedemptionDate(billDetails.getRedemptionDate());
//        if (billDetails.getRedemptionInterest() != null) existingBill.setRedemptionInterest(billDetails.getRedemptionInterest());
//        if (billDetails.getRedemptionTotal() != null) existingBill.setRedemptionTotal(billDetails.getRedemptionTotal());
//        if (billDetails.getRedemptionStatus() != null) existingBill.setRedemptionStatus(billDetails.getRedemptionStatus());
//        if (billDetails.getBillRedemSerial() != null) existingBill.setBillRedemSerial(billDetails.getBillRedemSerial());
//        if (billDetails.getBillRedemNo() != null) existingBill.setBillRedemNo(billDetails.getBillRedemNo());
//        if (billDetails.getComments() != null) existingBill.setComments(billDetails.getComments());
//        
//        
//
//        // New Calculation for Redemption Interest and Total
//        BigDecimal interestRateBD = existingBill.getRateOfInterest() != null ? 
//            existingBill.getRateOfInterest() : 
//            BigDecimal.valueOf(getRateOfInterest(existingBill.getProductTypeNo(), existingBill.getAmount()));
//
//        long daysBetween = ChronoUnit.DAYS.between(
//            existingBill.getBillDate(),
//            existingBill.getRedemptionDate() != null ? existingBill.getRedemptionDate() : LocalDate.now()
//        );
//
//        BigDecimal amountBD = BigDecimal.valueOf(existingBill.getAmount());
//        BigDecimal daysBetweenBD = BigDecimal.valueOf(daysBetween);
//
//        BigDecimal redemptionInterest = (amountBD.multiply(interestRateBD).multiply(daysBetweenBD))
//            .divide(BigDecimal.valueOf(36500), BigDecimal.ROUND_HALF_UP);
//        BigDecimal redemptionTotal = amountBD.add(redemptionInterest);
//
//        existingBill.setRedemptionInterest(redemptionInterest.doubleValue());
//        existingBill.setRedemptionTotal(redemptionTotal.doubleValue());
//
//        return billRepository.save(existingBill);
//    }
//
//    private double getRateOfInterest(Integer productTypeNo, Integer amount) {
//        if (productTypeNo == null) {
//            throw new IllegalArgumentException("Product type number cannot be null");
//        }
//
//        String productType = productTypeNo.toString();
//
//        if ("GOLD".equalsIgnoreCase(productType)) {
//            // Determine the interest rate based on the amount for GOLD
//            if (amount < 5000) return getSettingValue(44L); // GOLD_INTREST_LESS_THAN_5000
//            if (amount < 10000) return getSettingValue(45L); // GOLD_INTREST_LESS_THAN_10000
//            if (amount < 20000) return getSettingValue(46L); // GOLD_INTREST_LESS_THAN_20000
//            if (amount < 50000) return getSettingValue(47L); // GOLD_INTREST_LESS_THAN_50000
//            if (amount < 100000) return getSettingValue(48L); // GOLD_INTREST_LESS_THAN_100000
//            return getSettingValue(49L); // GOLD_INTREST_MORE_THAN_100000
//        } else if ("SILVER".equalsIgnoreCase(productType)) {
//            // Default interest rate for SILVER
//            return getSettingValue(50L); // SILVER_INTREST
//        }
//
//        // Throw an exception if the product type is invalid
//        throw new IllegalArgumentException("Invalid product type: " + productTypeNo);
//    }
//
//    private double getSettingValue(Long settingId) {
//        // Assume SettingsRepository has a method to find by id
//        Optional<Settings> settingOptional = settingsRepository.findById(settingId);
//        if (!settingOptional.isPresent()) {
//            throw new IllegalArgumentException("Setting with id " + settingId + " not found");
//        }
//        Settings setting = settingOptional.get();
//        try {
//            return Double.parseDouble(setting.getParamValue()); // Convert the string to double
//        } catch (NumberFormatException e) {
//            throw new IllegalArgumentException("Invalid number format for setting with id " + settingId, e);
//        }
//    }
	
	public Bill updateBill(Long id, Bill billDetails) {
	    Optional<Bill> billOptional = billRepository.findById(id);
	    if (!billOptional.isPresent()) {
	        throw new EntityNotFoundException("Bill with id " + id + " not found");
	    }

	    Bill existingBill = billOptional.get();
	    

	    // Update fields with provided billDetails
	    if (billDetails.getBillSerial() != null) existingBill.setBillSerial(billDetails.getBillSerial());
	    if (billDetails.getBillNo() != null) existingBill.setBillNo(billDetails.getBillNo());
	    if (billDetails.getBillDate() != null) existingBill.setBillDate(billDetails.getBillDate());
	    
	    // Handle the customer entity
	    if (billDetails.getCustomer() != null) {
            existingBill.setCustomer(getCustomer(existingBill));

//	        Customer customer = billDetails.getCustomer();
//	        if (customer.getCustomerid() != null) {
//	            Optional<Customer> customerOptional = customerRepository.findById(customer.getCustomerid());
//	            if (customerOptional.isPresent()) {
//	                existingBill.setCustomer(customerOptional.get());
//	            } else {
//	                throw new EntityNotFoundException("Customer with id " + customer.getCustomerid() + " not found");
//	            }
//	        } else {
//	            throw new IllegalArgumentException("Customer ID cannot be null");
//	        }
	    }

	    if (billDetails.getCareof() != null) existingBill.setCareof(billDetails.getCareof());
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
	    if (billDetails.getReceivedinterest() != null) existingBill.setReceivedinterest(billDetails.getReceivedinterest());
	    if (billDetails.getInterestinmonths() != null) existingBill.setInterestinmonths(billDetails.getInterestinmonths());

	    System.out.println("Bill Interst:"+billDetails.getReceivedinterest());
	    System.out.println("Months:"+billDetails.getInterestinmonths());
	    
	    

	   
        //calculateRedemption(existingBill);
	    return billRepository.save(existingBill);
	}
	
	private int monthsBetween(Bill bill) {
		  LocalDate redemptionDate = bill.getRedemptionDate() != null ? bill.getRedemptionDate() : LocalDate.now();
		    
		    // Calculate months between the two dates
		    return (int) ChronoUnit.MONTHS.between(bill.getBillDate().withDayOfMonth(1), redemptionDate.withDayOfMonth(1));

	}
	
	private BigDecimal getReceievedInterest(BigDecimal amountBD, int monthsBetween, double interestRateBD) {
		BigDecimal receievedInterest = amountBD.multiply(new BigDecimal(interestRateBD))
		        .multiply(BigDecimal.valueOf(monthsBetween-1))
		        .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP);
		return receievedInterest;
	}
	
	private Bill calculateRedemption(Bill existingBill,Map<String, String> settingsMap) {
		 // New Calculation for Redemption Interest and Total
		double interestRateBD = existingBill.getRateOfInterest() != null 
	        ? existingBill.getRateOfInterest().doubleValue() 
	        : getRateOfInterest(existingBill.getProductTypeNo().intValue(), existingBill.getAmount().intValue(),settingsMap);

	    LocalDate billDate = existingBill.getBillDate();
	    LocalDate redemptionDate = existingBill.getRedemptionDate() != null ? existingBill.getRedemptionDate() : LocalDate.now();
	    
	    // Calculate months between the two dates
	    int monthsBetween = (int) ChronoUnit.MONTHS.between(billDate.withDayOfMonth(1), redemptionDate.withDayOfMonth(1));

	    BigDecimal amountBD = BigDecimal.valueOf(existingBill.getAmount());
	    BigDecimal receievedInterest=getReceievedInterest(amountBD,monthsBetween,interestRateBD);
	    
	    BigDecimal redemptionTotal = amountBD.add(receievedInterest);
	    
	    double redemptioninterest = redemptioninterest(monthsBetween, amountBD.intValue(),settingsMap);

	    existingBill.setInterestinmonths(monthsBetween>0?monthsBetween:0);
	    existingBill.setReceivedinterest(receievedInterest.doubleValue());
	    existingBill.setRedemptionInterest(redemptioninterest);
	    existingBill.setRedemptionTotal(redemptionTotal.doubleValue());
	    return existingBill;
	}
	
	private double redemptioninterest(int monthsBetween,int amountBD, Map<String, String> settingsMap) {
		
		
		double rate = Double.parseDouble(settingsMap.get("REDEEM_INTERST"));
		

		
		// Convert the double values to BigDecimal for multiplication
		BigDecimal monthsBetweenBD = BigDecimal.valueOf(monthsBetween);//.add(BigDecimal.ONE);
		
		BigDecimal rateBD = BigDecimal.valueOf(rate);

		// Perform multiplication with BigDecimal
		BigDecimal redemptionInterestBD = monthsBetweenBD.multiply(rateBD).multiply(new BigDecimal(amountBD)).divide(new BigDecimal(100));

		// If you need to convert the result back to double
		double redemptionInterest = redemptionInterestBD.doubleValue();
		return redemptionInterest;
	}

	private double getRateOfInterest(int productTypeNo, int amount,Map<String, String> settingsMap) {
	    if (productTypeNo==0) {
	        throw new IllegalArgumentException("Product type number cannot be null");
	    }
	   
	    
	    //String productType = productTypeNo.toString();

	    double roi = 0;
	    

	    if (productTypeUtility.getmap().get("GOLD").getProductTypeNo() == productTypeNo) { // Assuming "1" is for GOLD
	       
	    	if (amount < 5000) roi = Double.parseDouble(settingsMap.get("GOLD_INTREST_LESS_THAN_5000")); // paramSeq = 44L; // GOLD_INTREST_LESS_THAN_5000
	        
	    	else if (amount < 10000) roi = Double.parseDouble(settingsMap.get("GOLD_INTREST_LESS_THAN_10000")); // GOLD_INTREST_LESS_THAN_10000
	        
	    	else if (amount < 20000) roi = Double.parseDouble(settingsMap.get("GOLD_INTREST_LESS_THAN_20000")); // GOLD_INTREST_LESS_THAN_20000
	        
	    	else if (amount < 50000) roi = Double.parseDouble(settingsMap.get("GOLD_INTREST_LESS_THAN_50000")); // GOLD_INTREST_LESS_THAN_50000
	        
	    	else if (amount < 100000) roi = Double.parseDouble(settingsMap.get("GOLD_INTREST_LESS_THAN_100000")); // GOLD_INTREST_LESS_THAN_100000
	        
	    	else roi = Double.parseDouble(settingsMap.get("GOLD_INTREST_MORE_THAN_100000")); // GOLD_INTREST_MORE_THAN_100000
	    }
	    
	    else if (productTypeUtility.getmap().get("SILVER").getProductTypeNo() == productTypeNo) { // Assuming "2" is for SILVER
	    	roi = Integer.parseInt(settingsMap.get("SILVER_INTREST")); // SILVER_INTREST
	    }
	    
	    else {
	        throw new IllegalArgumentException("Invalid product type: " + productTypeNo);
	    }

	    return roi;
	}

	private Map<String, String> getSettingMap() {
		return settingsUtillity.convertListToMap(settingsRepository.findAll());
	}
	
	 public boolean deleteRedeemBill(Character billSerial, Integer billNo) {
	        List<Bill> bill = billRepository.findByBillSerialAndBillNo(billSerial, billNo);
	        if (!bill.isEmpty()) {
	            billRepository.deleteAll(bill);
	            return true;
	        } else {
	            return false;
	        }
	    }
	 
//	 public ByteArrayInputStream generateAndSendBill(Long billSequence) {
//	        Bill bill = billRepository.findById(billSequence)
//	                .orElseThrow(() -> new IllegalArgumentException("Invalid billSeq"));
//	        ByteArrayInputStream in=null;
////	        // Check if bill is already uploaded
////	        if (bill.getBillUpload() != null) {
////	            // Bill already uploaded, send link to WhatsApp
////	            sendWhatsAppMessage(pledge.getBillUpload().getLink(), pledge.getCustomer().getPhoneno());
////	            return;
////	        }
//
//	        try {
//	            // Generate the PDF
//	            
//
//	            // Upload the PDF file
//	        	in = pdfService.generateAndSaveBillPdf(bill); // Retrieve the actual path
////	            FileUploadResponse response = fileUploadService.uploadFile(filePath);
////	            System.out.println("File uploaded");
////	            // Save the response to the database
////	            BillUpload billUpload = new BillUpload();
////	            billUpload.setUploadId(response.getFileId());
////	            billUpload.setPledge(pledge);
////	            billUpload.setLink(response.getLink());
////	            billUpload.setExpires(response.getExpires());
////	            billUpload.setAutoDelete(response.isAutoDelete());
////	            // billUpload.setPledge(pledge);
////
////	            billUploadRepository.save(billUpload);
////
////	            // Associate bill upload with the pledge
////	            pledge.setBillUpload(billUpload);
////	            pledgeRepository.save(pledge);
////
////	            // Send WhatsApp message with the link
////	            sendWhatsAppMessage(response.getLink(), pledge.getCustomer().getPhoneno());
//
//	        } catch (IOException | DocumentException e) {
//	            e.printStackTrace();
//	        }
//	        return in;
//	    }
	 
	 public ByteArrayInputStream generateCustomerSendBill(Bill bill,  Map<String, String> settingsMap) {
	        ByteArrayInputStream in = null;

	        try {
	            // Generate the PDF
	            in = customerPdfService.generateCustomerBillPdf(bill,settingsMap); // Retrieve the PDF as a ByteArrayInputStream
	        } catch (IOException | DocumentException e) {
	            e.printStackTrace();
	            throw new RuntimeException("Error generating PDF: " + e.getMessage());
	        }
	        return in;
	    }
	 
	 public ByteArrayInputStream generateOfficeSendBill(Bill bill,  Map<String, String> settingsMap) {
	        ByteArrayInputStream in = null;

	        try {
	            // Generate the PDF
	            in = officePdfService.generateOfficeBillPdf(bill,settingsMap); // Retrieve the PDF as a ByteArrayInputStream
	        } catch (IOException | DocumentException e) {
	            e.printStackTrace();
	            throw new RuntimeException("Error generating PDF: " + e.getMessage());
	        }
	        return in;
	    }
	 
	 public ByteArrayInputStream generateAndRedeemBillPdf(Bill bill,  Map<String, String> settingsMap) {
	        ByteArrayInputStream in = null;

	        try {
	            // Generate the PDF
	            in = pdfRedeemService.generateAndSaveRedeemBillPdf(bill,settingsMap); // Retrieve the PDF as a ByteArrayInputStream
	        } catch (IOException | DocumentException e) {
	            e.printStackTrace();
	            throw new RuntimeException("Error generating PDF: " + e.getMessage());
	        }
	        return in;
	    }
	 
	 


}
