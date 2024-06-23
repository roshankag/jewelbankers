package com.jewelbankers.services;

import com.jewelbankers.entity.Bill;
import com.jewelbankers.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BillService {
    @Autowired
    private BillRepository billRepository;
    
    public List<Bill> findBillsByBillSequence(int billSequence) {
        return billRepository.findById(billSequence);
    }

    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    public Optional<Bill> findById(Integer billSequence){ 
        return billRepository.findById(billSequence);
    }

    public Bill saveBill(Bill bill) {
        return billRepository.save(bill);
    }

    public void deleteBill(Integer id) {
        billRepository.deleteById(id);
    }
}
