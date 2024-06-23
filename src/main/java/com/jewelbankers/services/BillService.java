package com.jewelbankers.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jewelbankers.entity.Bill;
import com.jewelbankers.repository.BillRepository;

@Service
public class BillService {
    @Autowired
    private BillRepository billRepository;
    
    public List<Bill> findBillsByCustomerName(String customerName, String street, Integer billNo) {
        return billRepository.findByCustomerCustomerNameOrCustomerStreetOrBillNo(customerName, street,billNo);
    }
    
    public List<Bill> findBillsByCustomerStreet(String street) {
        return billRepository.findByCustomerStreet(street);
    }

    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    public Optional<Bill> findById(Long billSequence){ 
        return billRepository.findById(billSequence);
    }

    public Bill saveBill(Bill bill) {
        return billRepository.save(bill);
    }

    public void deleteBill(Long id) {
        billRepository.deleteById(id);
    }

	public List<Bill> findBillsByBillSequence(int billSequence) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
