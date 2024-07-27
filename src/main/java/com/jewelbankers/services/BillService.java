package com.jewelbankers.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jewelbankers.Utility.BillUtility;
import com.jewelbankers.entity.Bill;
import com.jewelbankers.repository.BillRepository;

@Service
public class BillService {
	@Autowired
	private BillRepository billRepository;
	
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
