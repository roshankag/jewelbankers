package com.jewelbankers.services;

import java.util.List;
import java.util.Optional;

import org.apache.catalina.util.StringUtil;
import org.apache.catalina.util.ToStringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jewelbankers.Utility.BillUtility;
import com.jewelbankers.entity.Bill;
import com.jewelbankers.repository.BillRepository;

@Service
public class BillService {
	@Autowired
	private BillRepository billRepository;
	
//	public int getNextBillNumber() {
//        Bill lastBill = billRepository.findTopByOrderByIdDesc();
//        if (lastBill != null) {
//            return lastBill.getBillNo() + 1;
//        }
//        return 1; // Start from 1 if no bills exist
//    }

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

	/*
	 * public List<Bill> getAllBills() { return billRepository.findAll(); }
	 */

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

			
	
	/*
	 * public List<Bill> findBillsByBillSequence(Long billSeq) { return
	 * billRepository.findByBillSequence(billSeq); }
	 */
	  
	  public List<Bill> findBillsByBillNo(Character billSerial,Integer billNo, Long billSequence ) 
	  { 
		  if(billNo != null && billNo >0) return billRepository.findByBillSerialAndBillNo(billSerial,billNo);
		  else return billRepository.findByBillSequence(billSequence);
	  
	  }
	 
}
