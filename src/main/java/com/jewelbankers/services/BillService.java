package com.jewelbankers.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.itextpdf.text.DocumentException;
import com.jewelbankers.Utility.BillUtility;
import com.jewelbankers.Utility.ProductTypeUtility;
import com.jewelbankers.Utility.SettingsUtillity;
import com.jewelbankers.Utility.TimeFormatterUtil;
import com.jewelbankers.entity.Bill;
import com.jewelbankers.entity.BillDetail;
import com.jewelbankers.entity.Customer;
import com.jewelbankers.excel.ExcelGenerator;
import com.jewelbankers.repository.BillDetailRepository;
import com.jewelbankers.repository.BillRepository;
import com.jewelbankers.repository.CustomerRepository;
import com.jewelbankers.repository.ProductTypeRepository;
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
	
	// Constants for redemption status
    private static final char REDEMPTION_STATUS_OPEN = 'O';
    
    private static final char REDEMPTION_STATUS_CLOSED = 'C';
    
	@Autowired
	private BillRepository billRepository;
	
	@Autowired
	private BillDetailRepository billDetailRepository;
	
	@Autowired
	private ProductTypeRepository productTypeRepository;
	
	@Autowired
    private SettingsService settingsService;
	
	@Autowired
    private SettingsRepository settingsRepository;
	
	 @Autowired
	 private CustomerRepository customerRepository;
	 
	 @Autowired
	 private EntityManager entityManager;
	 
	 @Autowired
	 private OfficePdfService officePdfService;
	
	 @Autowired
	 SettingsUtillity settingsUtillity; 
	 
	 @Autowired
	 ProductTypeUtility productTypeUtility;
	 
	 @Autowired
	 private PdfRedeemService pdfRedeemService;
	 
	 @Autowired
	 private PdfService pdfService;
	 
	 
	public List<Bill> findBillsByProductTypeNo(Long productTypeNo) {
        return billRepository.findByProductTypeNo(productTypeNo);
    }
	
	public List<Bill> findBillsByRedemptionStatus(Character redemptionStatus) {
        return billRepository.findByRedemptionStatus(redemptionStatus);
    }
	
	public BillService(BillRepository billRepository) {
        this.billRepository = billRepository;
    }
    
	public ByteArrayInputStream exportBillsToExcel(String search, LocalDate fromDate, LocalDate toDate, Integer amount, Character status, 
			Integer productTypeNo, String sortOrder, Long phoneno) throws IOException {
        // Retrieve the list of bills based on the search criteria
        List<Bill> bills = findBillsBySearch(search, fromDate, toDate, amount, status, productTypeNo, sortOrder, phoneno);

        if (bills.isEmpty()) {
            return null; // Return null if no bills are found
        }

        // Fetch the settings and get the password for the Excel file
        Map<String, String> settingsMap = settingsUtillity.convertListToMap(settingsRepository.findAll());
        String password = settingsUtillity.getExcelPassword(settingsMap);

        // Add 100 to the password (as per your requirement)
        try {
            Integer passwordNumber = Integer.parseInt(password);
            password = String.valueOf(passwordNumber + 100);
        } catch (NumberFormatException e) {
            // Handle invalid password format if necessary
            password = "defaultPassword"; // Fallback password if the format is invalid
        }

        // Generate the Excel file from the list of bills with the dynamic password
        return ExcelGenerator.generateBillExcel(bills, password, productTypeRepository);
    }

	
	public List<Bill> findBillsByCustomerName(String customerName, String street, Integer billNo) {
		return billRepository.findByCustomerCustomerNameOrCustomerStreetOrBillNo(customerName, street, billNo);
	}
	
	
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
	
	public List<Bill> findBillsBySearch(String search, LocalDate fromDate, LocalDate toDate, Integer amount, Character status, Integer productTypeNo, String sortOrder, Long phoneno) {
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
                    
                 // Handle search by Phone Number
                    if (phoneno != null) {
                        predicates.add(cb.equal(root.get("customer").get("phoneno"), phoneno));
                    }
                    
                 // Handle date filtering based on status
                    if (status != null && status == 'R') {
                        // Use redemptionDate if status is "R"
                        if (fromDate != null && toDate != null) {
                            predicates.add(cb.between(root.get("redemptionDate"), fromDate, toDate));
                        } else if (fromDate != null) {
                            predicates.add(cb.greaterThanOrEqualTo(root.get("redemptionDate"), fromDate));
                        } else if (toDate != null) {
                            predicates.add(cb.lessThanOrEqualTo(root.get("redemptionDate"), toDate));
                        }
                    } else {
                        // Use billDate for other statuses
                        if (fromDate != null && toDate != null) {
                            predicates.add(cb.between(root.get("billDate"), fromDate, toDate));
                        } else if (fromDate != null) {
                            predicates.add(cb.greaterThanOrEqualTo(root.get("billDate"), fromDate));
                        } else if (toDate != null) {
                            predicates.add(cb.lessThanOrEqualTo(root.get("billDate"), toDate));
                        }
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
                    if (sortOrder != null && sortOrder.equalsIgnoreCase("customername")) {
                        query.orderBy(cb.asc(root.get("customer").get("customerName")));
                    } else if (status != null && status == 'R') {
                        query.orderBy(
                            cb.desc(root.get("redemptionDate")),
                            cb.desc(root.get("billRedemSerial")), // Sorting by BILL_REDEM_SERIAL
                            cb.desc(root.get("billRedemNo"))      // Sorting by BILL_REDEM_NO
                        );
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
            
         // Print product descriptions for each bill found
            for (Bill bill : bills) {
                //System.out.println("bill sequence: " + bill.getBillSequence()); // Adjust this to the actual method/property for description
                //System.out.println("Product Description: " + bill.getProductDescriptions()); // Adjust this to the actual method/property for description
                
//                for (BillDetail billDetail : bill.getBillDetails()) {
//
//	            }
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

	// Method to save bill without an image
	@Transactional
	public Bill saveBill(Bill bill, MultipartFile photo, MultipartFile articlephoto) throws IOException {
		 // Set the current time as pledge time (this will be stored as LocalTime)
        String pledgeTime = LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm a"));
        //System.out.println("pledgeTime"+pledgeTime);
        bill.setPledgetime(pledgeTime);
        System.out.println("Formatted Pledge Time: " + pledgeTime);
		if(bill.getCustomer().getCustomerid() != null) {			
	            // Set the updated customer back to the bill
	            bill.setCustomer(setCustomer(bill, photo));
	    }
		 if (photo != null && !photo.isEmpty()) {
	          byte[] photoBytes = photo.getBytes();
	          bill.getCustomer().setPhoto(photoBytes);
	          
	       // Process articlePhoto and assign it to the first BillDetail item
	          if (articlephoto != null && !articlephoto.isEmpty() && bill.getBillDetails() != null && !bill.getBillDetails().isEmpty()) {
	              byte[] articlephotobytes = articlephoto.getBytes();
	              bill.getBillDetails().get(0).setArticlephoto(articlephotobytes);
	          }
	          // Optional: Convert photo to Base64 and store it in the transient field for easy JSON transmission
	          //String photoBase64 = Base64.getEncoder().encodeToString(photoBytes);
	          //customer.setPhotoBase64(photoBase64);
	      }
	    return billRepository.save(bill);
	}
	
	public Customer setCustomer(Bill bill, MultipartFile photo) throws IOException {
	    Customer incomingCustomer = bill.getCustomer();
	    Customer customer;

	    // Check if the customer ID exists for an update, otherwise create a new customer
	    if (incomingCustomer.getCustomerid() != null) {
	        Optional<Customer> optionalCustomer = customerRepository.findById(incomingCustomer.getCustomerid());

	        if (optionalCustomer.isPresent()) {
	            // Existing customer from the database
	            customer = optionalCustomer.get();
	        } else {
	            // If customer ID is provided but not found, create a new customer
	            customer = new Customer();
	        }
	    } else {
	        // New customer case
	        customer = new Customer();
	    }

	    // Common setters for both new and existing customers
	    customer.setCustomerName(incomingCustomer.getCustomerName());
	    customer.setPhoneno(incomingCustomer.getPhoneno());
	    customer.setAddress(incomingCustomer.getAddress());
	    customer.setMailid(incomingCustomer.getMailid());
	    customer.setProofType(incomingCustomer.getProofType());
	    customer.setProofDetails(incomingCustomer.getProofDetails());

	 // Convert the MultipartFile (photo) to a byte array
      if (photo != null && !photo.isEmpty()) {
          byte[] photoBytes = photo.getBytes();
          customer.setPhoto(photoBytes);
          
          // Optional: Convert photo to Base64 and store it in the transient field for easy JSON transmission
          //String photoBase64 = Base64.getEncoder().encodeToString(photoBytes);
          //customer.setPhotoBase64(photoBase64);
      }
	    // Save and return the customer (new or updated)
	    return customerRepository.save(customer);
	}

    @Transactional
    public Bill updateBill(Long billSequence, Bill bill, MultipartFile photo, MultipartFile articlephoto) throws IOException {
        // Fetch the existing bill from the repository
        Optional<Bill> optionalBill = billRepository.findById(billSequence);
        
        if (optionalBill.isPresent()) {
            //System.out.println("Bill Exists **********");
            Bill existingBill = optionalBill.get();

            // Update the fields from the provided bill object
            existingBill.setBillSerial(bill.getBillSerial());
            existingBill.setBillNo(bill.getBillNo());
            existingBill.setBillDate(bill.getBillDate());
          
            // Set the updated customer back to the bill
            Customer updatedCustomer = setCustomer(bill, photo);
    	   existingBill.setCustomer(updatedCustomer);

            existingBill.setProductTypeNo(bill.getProductTypeNo());
            existingBill.setAmount(bill.getAmount());
            existingBill.setGrams(bill.getGrams());
            existingBill.setComments(bill.getComments());
            
            if (bill.getOldbillserialno() != null) {
                existingBill.setOldbillserialno(bill.getOldbillserialno());
            } else {
                // Handle the case when oldbillserialno is null
                existingBill.setOldbillserialno(null); // Or any default value if necessary
            }

            existingBill.setRateOfInterest(bill.getRateOfInterest());
            existingBill.setPresentValue(bill.getPresentValue());
            existingBill.setAmountInWords(bill.getAmountInWords());
            existingBill.setMonthlyIncome(bill.getMonthlyIncome());
            
//         // If pledgeTime is updated, format it
//            if (bill.getPledgetime() != null) {
//                // Set the new pledgeTime (no formatting here, save as LocalTime)
//                existingBill.setPledgeTime(bill.getPledgetime());
//
//                // Format the pledgeTime to 12-hour format (for display purposes)
//                String formattedPledgeTime = TimeFormatterUtil.formatTo12Hour(existingBill.getPledgeTime());
//                //System.out.println("Formatted Pledge Time: " + formattedPledgeTime);
//            }

            // Update ProductDetails (productDescription and productQuantity)
            List<BillDetail> existingBillDetails = existingBill.getBillDetails(); // Get existing details

            // Create a map for quick lookup of existing details by productNo
            Map<Integer, BillDetail> existingDetailMap = existingBillDetails.stream()
                .collect(Collectors.toMap(BillDetail::getProductNo, detail -> detail));

            // Create a list to track productNos from the incoming bill details
            Set<Integer> incomingProductNos = bill.getBillDetails().stream()
                .map(BillDetail::getProductNo)
                .collect(Collectors.toSet());

            // Iterate over incoming bill details
            for (BillDetail detail : bill.getBillDetails()) {
                if (existingDetailMap.containsKey(detail.getProductNo())) {
                    // Update existing BillDetail
                    BillDetail existingDetail = existingDetailMap.get(detail.getProductNo());
                    existingDetail.setProductDescription(detail.getProductDescription());
                    existingDetail.setProductQuantity(detail.getProductQuantity());
                    
                    // Set articlephoto for this BillDetail if provided
                    if (articlephoto != null && !articlephoto.isEmpty()) {
                        byte[] articlePhotoBytes = articlephoto.getBytes();
                        existingDetail.setArticlephoto(articlePhotoBytes);
                    }
                    
                } else {
                    // Add new BillDetail if it doesn't exist
                    BillDetail newDetail = new BillDetail();
                    newDetail.setProductNo(detail.getProductNo());
                    newDetail.setProductDescription(detail.getProductDescription());
                    newDetail.setProductQuantity(detail.getProductQuantity());
                    
                 // Set articlephoto for new BillDetail if provided
                    if (articlephoto != null && !articlephoto.isEmpty()) {
                        byte[] articlePhotoBytes = articlephoto.getBytes();
                        newDetail.setArticlephoto(articlePhotoBytes);
                    }

                    // Set the relationship back to existingBill if necessary
                    newDetail.setBill(existingBill);

                    // Add the new detail to the existing list
                    existingBillDetails.add(newDetail);
                }
            }

            // Remove outdated BillDetails that are not in the incoming list
            existingBillDetails.removeIf(existingDetail -> !incomingProductNos.contains(existingDetail.getProductNo()));

            // Save and return the updated bill
            return billRepository.save(existingBill);

        } else {
            // Handle case where the bill with the specified id does not exist
            throw new EntityNotFoundException("Bill not found with id " + billSequence);
        }
    }


	// Example method to get shop details and include in the bill
    public Map<String, String> getShopDetailsForBill() {
        return settingsService.getShopDetails();
    }
	
	public void deleteBill(Long id) {
		billRepository.deleteById(id);
	}
	
	public int getNextBillNo() {
        // Logic to fetch the next available bill number
        Integer currentBillNo = billRepository.findCurrentBillNo();
        return (currentBillNo == null) ? 0 : currentBillNo + 1;
    }
	
	public int getNextBillRedemNo() {
        // Logic to fetch the next available redeem number
        Integer currentBillNo = billRepository. findCurrentBillRedemNo();
        return (currentBillNo == null) ? 0 : currentBillNo + 1;
    }
	public List<Bill> findBillsByBillNo(Character billSerial, Integer billNo, Long billSequence, LocalDate chosenRedemptionDate) { 
	    Map<String, String> settingsMap = getSettingMap();

	    if (billNo != null && billNo > 0) {
	        List<Bill> bills = billRepository.findByBillSerialAndBillNo(billSerial, billNo);
	        for (Bill bill2 : bills) {
	            // Pass the chosenRedemptionDate to the monthsBetween method
	            int monthsBetween = monthsBetween(bill2, chosenRedemptionDate != null ? chosenRedemptionDate : LocalDate.now());
	            bill2.setInterestinmonths(monthsBetween);
	            
	            // Use user-provided rate or fallback to calculated rate
	            double rateOfInterest = bill2.getRateOfInterest() != null
	                ? bill2.getRateOfInterest().doubleValue()
	                : getRateOfInterest(bill2.getProductTypeNo().intValue(), bill2.getAmount().intValue(), settingsMap);

	            BigDecimal receivedInterest = getReceievedInterest(
	                BigDecimal.valueOf(bill2.getAmount()),
	                monthsBetween,
	                rateOfInterest
	            );
	            
	            bill2.setReceivedinterest(receivedInterest.doubleValue());
	            bill2.setRedemptionInterest(redemptioninterest(monthsBetween, bill2.getAmount(), settingsMap));
	        }
	        return bills;
	    } else {
	        return billRepository.findByBillSequence(billSequence);
	    }
	}

		
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

		/*
		 * System.out.println("Bill Interst:"+billDetails.getReceivedinterest());
		 * System.out.println("Months:"+billDetails.getInterestinmonths());
		 */   
	 // If redeemTime is updated, format it
	    if (billDetails.getRedeemTime() != null) {
	        // Set the new redeemTime (no formatting here, save as LocalTime)
	        existingBill.setRedeemTime(billDetails.getRedeemTime());

	        // Format the redeemTime to 12-hour format (for display purposes)
	        String formattedRedeemTime = TimeFormatterUtil.formatTo12Hour(existingBill.getRedeemTime());
	        //System.out.println("Formatted Redeem Time: " + formattedRedeemTime);
	    }
	    
        //calculateRedemption(existingBill);
	    return billRepository.save(existingBill);
	}
	
	private int monthsBetween(Bill bill, LocalDate chosenRedemptionDate) {
	    // Get the bill date
	    LocalDate billDate = bill.getBillDate();

	    // Use the chosen redemption date or the current date if it's not provided
	    LocalDate redemptionDate = chosenRedemptionDate != null ? chosenRedemptionDate : LocalDate.now();

	    // Calculate the number of months between the bill date and redemption date
	    int monthsBetween = (int) ChronoUnit.MONTHS.between(
	        billDate.withDayOfMonth(1),
	        redemptionDate.withDayOfMonth(1)
	    );

	    // Adjust the result based on whether the redemption date's day has passed the bill date's day of the month
	    if (redemptionDate.getDayOfMonth() < billDate.getDayOfMonth()) {
	        monthsBetween--;
	    }

	    return Math.max(monthsBetween, 0); // Ensure the result is non-negative
	}


	
	private BigDecimal getReceievedInterest(BigDecimal amountBD, int monthsBetween, double interestRateBD) {
	BigDecimal receievedInterest = new BigDecimal(0.0);
	if(monthsBetween>0) {

	 receievedInterest = amountBD.multiply(new BigDecimal(interestRateBD))
		        .multiply(BigDecimal.valueOf(monthsBetween))
		        .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP);
	 System.out.println("Recieved Interst:" + receievedInterest);
		return receievedInterest;
	}
	return receievedInterest;	
	}
	
	private Bill calculateRedemption(Bill existingBill,Map<String, String> settingsMap) {
		 // New Calculation for Redemption Interest and Total
		double interestRateBD = existingBill.getRateOfInterest() != null 
	        ? existingBill.getRateOfInterest().doubleValue() 
	        : getRateOfInterest(existingBill.getProductTypeNo().intValue(), existingBill.getAmount().intValue(),settingsMap);
		
//		double interestRateBD = existingBill.getRateOfInterest() != null
//			    ? existingBill.getRateOfInterest().doubleValue() // Convert BigDecimal to double
//			    : BigDecimal.ZERO.doubleValue(); // Fallback to zero as double

	    LocalDate billDate = existingBill.getBillDate();
	    LocalDate redemptionDate = existingBill.getRedemptionDate() != null ? existingBill.getRedemptionDate() : LocalDate.now();
	    
	    // Calculate months between the two dates
	    int monthsBetween = (int) ChronoUnit.MONTHS.between(billDate.withDayOfMonth(1), redemptionDate.withDayOfMonth(1));

	    BigDecimal amountBD = BigDecimal.valueOf(existingBill.getAmount());
	    BigDecimal receievedInterest=getReceievedInterest(amountBD,monthsBetween,interestRateBD);
	    
	    BigDecimal total = amountBD.add(receievedInterest);
	    
	    if (existingBill.getAmountpaid() != null) {
	        // Convert Integer to BigDecimal for subtraction
	        BigDecimal amountPaid = BigDecimal.valueOf(existingBill.getAmountpaid());
	        BigDecimal balance = total.subtract(amountPaid);

	        // Set balance and redemption status
	        existingBill.setBalance(balance.intValue()); // Convert BigDecimal to Integer

	        if (balance.compareTo(BigDecimal.ZERO) > 0) {
	            // Partial payment made
	            existingBill.setRedemptionStatus('P'); // Par	tial
	        } else {
	            // Full payment made
	            existingBill.setRedemptionStatus('R'); // Fully Redeemed
	        }
	    } else {
	        // No payment made
	        existingBill.setBalance(total.intValue()); // Convert BigDecimal to Integer
	        existingBill.setRedemptionStatus('O'); // Open
	    }

	    
	    BigDecimal redemptionTotal = amountBD.add(receievedInterest);
	    
	    double redemptioninterest = redemptioninterest(monthsBetween, amountBD.intValue(),settingsMap);

	    existingBill.setInterestinmonths(monthsBetween>0?monthsBetween:0);
	    existingBill.setReceivedinterest(receievedInterest.doubleValue());
	    existingBill.setRedemptionInterest(redemptioninterest);
	    existingBill.setRedemptionTotal(redemptionTotal.doubleValue());
	    return existingBill;
	}
	
	private double redemptioninterest(int monthsBetween, int amountBD, Map<String, String> settingsMap) {
	    double rate = Double.parseDouble(settingsMap.get("REDEEM_INTERST"));
	    BigDecimal rateBD = BigDecimal.valueOf(rate);
	    BigDecimal amountBDValue = BigDecimal.valueOf(amountBD);

	    // Adjust months for calculation
	    int adjustedMonths = monthsBetween == 0 ? 1 : monthsBetween+1; 

	    // Calculate interest
	    BigDecimal monthsBetweenBD = BigDecimal.valueOf(adjustedMonths);
	    BigDecimal redemptionInterestBD = monthsBetweenBD
	        .multiply(rateBD)
	        .multiply(amountBDValue)
	        .divide(BigDecimal.valueOf(100));

	    // Return the calculated interest as a double
	    return redemptionInterestBD.doubleValue();
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
	 
	 
//	 public ByteArrayInputStream generateCustomerSendBill(Bill bill,  Map<String, String> settingsMap) {
//	        ByteArrayInputStream in = null;
//
//	        try {
//	            // Generate the PDF
//	            in = customerPdfService.generateCustomerBillPdf(bill,settingsMap); // Retrieve the PDF as a ByteArrayInputStream
//	        } catch (IOException | DocumentException e) {
//	            e.printStackTrace();
//	            throw new RuntimeException("Error generating PDF: " + e.getMessage());
//	        }
//	        return in;
//	    }
	 
	 public ByteArrayInputStream generateSendBill(Bill bill, Map<String, String> settingsMap) {
		    ByteArrayInputStream in = null;

		    try {
		        // Generate the PDF using PdfService
		        in = pdfService.generatePdf(bill, settingsMap);  // Retrieve the PDF as a ByteArrayInputStream
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
	 
	 
	 public List<Map<String, Object>> getOpenBillsForCustomer(Long customerid) {
		    // Fetch bills where redemptionStatus is 'O' (open bills)
		    List<Bill> bills = billRepository.findByCustomerCustomeridAndRedemptionStatus(customerid, REDEMPTION_STATUS_OPEN);

		    return bills.stream().map(bill -> {
		        int monthsDuration = monthsDuration(bill);
		        BigDecimal amountBD = BigDecimal.valueOf(bill.getAmount());
		        double interestRateBD = bill.getRateOfInterest().doubleValue();

		        BigDecimal interest = getInterest(amountBD, monthsDuration, interestRateBD);
		        BigDecimal total = calculateTotal(amountBD, interest);
		        
		        bill.setNoOfMonths(monthsDuration);
	            bill.setInterest(interest);
	            bill.setTotal(total);

		        // Create a map with only the needed fields
		        Map<String, Object> resultMap = new HashMap<>();
		        
		        resultMap.put("billSerial", bill.getBillSerial());
		        resultMap.put("billNo", bill.getBillNo());
		        resultMap.put("billDate", bill.getBillDate());
		        resultMap.put("weight", bill.getGrams());
		        resultMap.put("amount", bill.getAmount());
		        resultMap.put("noOfMonths", bill.getNoOfMonths());
		        resultMap.put("interest", bill.getInterest());
		        resultMap.put("total", bill.getTotal());
		        resultMap.put("productDescription", bill.getBillDetails().get(0).getProductDescription()); // assuming this field is in Bill.java

		        return resultMap;
		    }).collect(Collectors.toList());
		}

	 
	 public List<Map<String, Object>> getOpenBillsForCustomer(String customerName) {
		    // Fetch bills where redemptionStatus is 'O' (open bills) using customerName
		    List<Bill> bills = billRepository.findByCustomerCustomerNameAndRedemptionStatus(customerName, REDEMPTION_STATUS_OPEN);

		    return bills.stream().map(bill -> {
		        int monthsDuration = monthsDuration(bill);
		        BigDecimal amountBD = BigDecimal.valueOf(bill.getAmount());
		        double interestRateBD = bill.getRateOfInterest().doubleValue();

		        BigDecimal interest = getInterest(amountBD, monthsDuration, interestRateBD);
		        BigDecimal total = calculateTotal(amountBD, interest);

		        // Assuming you have setters or transient fields for these calculated values
		        bill.setNoOfMonths(monthsDuration);
		        bill.setInterest(interest);
		        bill.setTotal(total);

		        // Create a map with only the needed fields
		        Map<String, Object> resultMap = new HashMap<>();

		        resultMap.put("billSerial", bill.getBillSerial());
		        resultMap.put("billNo", bill.getBillNo());
		        resultMap.put("billDate", bill.getBillDate());
		        resultMap.put("weight", bill.getGrams());
		        resultMap.put("amount", bill.getAmount());
		        resultMap.put("noOfMonths", bill.getNoOfMonths());
		        resultMap.put("interest", bill.getInterest());
		        resultMap.put("total", bill.getTotal());
		        if (bill != null && bill.getBillDetails() != null && !bill.getBillDetails().isEmpty() 
		                && bill.getBillDetails().get(0).getProductDescription() != null) {
		            resultMap.put("productDescription", bill.getBillDetails().get(0).getProductDescription());
		        } else {
		            resultMap.put("productDescription", ""); // Default value if null
		        }
		        return resultMap;

		    }).collect(Collectors.toList());
		}

		private int monthsDuration(Bill bill) {
		    LocalDate billDate = bill.getBillDate();
		    LocalDate currentDate = LocalDate.now();

		    // If the current date is before the bill date (e.g., the bill is in the future), return 0 months
		    if (currentDate.isBefore(billDate)) {
		        return 0; // No months have passed yet
		    }

		    // If the current date is before the bill's anniversary in the current month, return 0 months
		    if (currentDate.getMonthValue() == billDate.getMonthValue() && currentDate.getDayOfMonth() < billDate.getDayOfMonth()) {
		        return 0;
		    }

		    // Calculate the difference in years and months between the bill date and current date
		    int yearDiff = currentDate.getYear() - billDate.getYear();
		    int monthDiff = currentDate.getMonthValue() - billDate.getMonthValue();

		    // Calculate total months between bill date and current date
		    int months = yearDiff * 12 + monthDiff;

		    // If the current day is before the bill's day of the current month, subtract one month
		    if (currentDate.getDayOfMonth() < billDate.getDayOfMonth()) {
		        months--; // Subtract 1 month as the current month is not complete yet
		    }

		    // Ensure the months count is non-negative (e.g., if the current date is still in the same month as the bill date)
		    return Math.max(months, 0);
		}

		private BigDecimal getInterest(BigDecimal amount, int monthsDuration, double interestRate) {
		    // If no months have passed, no interest should be calculated
		    if (monthsDuration <= 0) {
		        return BigDecimal.ZERO;
		    }

		    // Calculate the interest
		    return amount.multiply(BigDecimal.valueOf(interestRate))
		                 .multiply(BigDecimal.valueOf(monthsDuration))
		                 .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP);
		}

		private BigDecimal calculateTotal(BigDecimal amount, BigDecimal interest) {
		    return amount.add(interest);
		}
		
		public List<String> getAllProductDescriptions(String prefix) {
		    Pageable pageable = PageRequest.of(0, 25); // Limit to 100 records
		    return billDetailRepository.findProductDescriptionsByPrefix(prefix, pageable);
		}



}