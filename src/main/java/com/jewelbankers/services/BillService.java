package com.jewelbankers.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
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

import com.jewelbankers.Utility.BillUtility;
import com.jewelbankers.entity.Bill;
import com.jewelbankers.excel.ExcelGenerator;
import com.jewelbankers.repository.BillRepository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class BillService {
	@Autowired
	private BillRepository billRepository;
	
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
	
	public List<Bill> findBillsBySearch(String search, String fromDate, String toDate, Integer amount, Character status, Integer productTypeNo) {
        return billRepository.findAll(new Specification<Bill>() {
        	
        	
            @Override
            public Predicate toPredicate(Root<Bill> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();                
            	 Predicate searchPredicate;
                if (search != null && !search.isEmpty() && BillUtility.ValidateBillNo(search) ) {
                	Character billSerial = null;
                	Integer billNo=null;
                	System.out.println("Bill"+search.charAt(0)+":"+Integer.parseInt(search.substring(1, search.length())));
        			billSerial = search.toUpperCase().charAt(0);
        			billNo =Integer.parseInt(search.substring(1, search.length()));
                    searchPredicate = cb.or(
                    		cb.like(root.get("billSerial"), "%" + billSerial + "%"),
                            cb.like(root.get("billNo"), "%" + billNo + "%")
                    );
                }else if(search != null && !search.isEmpty() ){
                	
                    searchPredicate = cb.or(
                            cb.like(root.get("customer").get("customerName"), "%" + search + "%")
                    );
                    predicates.add(searchPredicate);
                }

				/*
				 * if (fromDate != null && toDate != null) {
				 * predicates.add(cb.between(root.get("billDate"), fromDate, toDate)); } else
				 */
                if (fromDate != null) {
                    predicates.add(cb.greaterThanOrEqualTo(root.get("billDate"), fromDate));
                } else if (toDate != null) {
                    predicates.add(cb.lessThanOrEqualTo(root.get("billDate"), toDate));
                }

                if (amount != null) {
                    predicates.add(cb.equal(root.get("amount"), amount));
                }

                if (status != null) {
                    predicates.add(cb.equal(root.get("redemptionStatus"), status));
                }

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

	public Bill saveBill(Bill bill) {
		return billRepository.save(bill);
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
