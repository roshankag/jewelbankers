package com.balaji.springjwt.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.balaji.springjwt.models.Pledge;
import com.balaji.springjwt.repository.PledgeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PledgeService {

    @Autowired
    private PledgeRepository pledgeRepository;

    public List<Pledge> getAllPledges() {
        return pledgeRepository.findAll();
    }

    public Optional<Pledge> getPledgeById(Long id) {
        return pledgeRepository.findById(id);
    }

    public List<Pledge> getPledgesByCustomerId(Long customerId) {
        return pledgeRepository.findByCustomer_CustomerId(customerId);
    }

    public Pledge savePledge(Pledge pledge) {
        return pledgeRepository.save(pledge);
    }

    public Pledge updatePledge(Long id, Pledge pledgeDetails) {
        Pledge pledge = pledgeRepository.findById(id).orElseThrow(() -> new RuntimeException("Pledge not found"));

        pledge.setBillNo(pledgeDetails.getBillNo());
        pledge.setProductType(pledgeDetails.getProductType());
        pledge.setAmount(pledgeDetails.getAmount());
        pledge.setRateOfInterest(pledgeDetails.getRateOfInterest());
        pledge.setPresentValue(pledgeDetails.getPresentValue());
        pledge.setBillDate(pledgeDetails.getBillDate());
        pledge.setGrams(pledgeDetails.getGrams());
        pledge.setProductQuantity(pledgeDetails.getProductQuantity());
        pledge.setProductDescription(pledgeDetails.getProductDescription());
        pledge.setAmountInWords(pledgeDetails.getAmountInWords());
        pledge.setInterestPledge(pledgeDetails.getInterestPledge());
        pledge.setTotalAmount(pledgeDetails.getTotalAmount());

        return pledgeRepository.save(pledge);
    }

    public void deletePledge(Long id) {
        Pledge pledge = pledgeRepository.findById(id).orElseThrow(() -> new RuntimeException("Pledge not found"));
        pledgeRepository.delete(pledge);
    }
}
