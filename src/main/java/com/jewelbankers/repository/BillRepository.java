package com.jewelbankers.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jewelbankers.entity.Bill;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
	List<Bill> findByBillSequence(Long billSequence);
	//List<Bill> findByCustomer(Customer customer);
	List<Bill> findByCustomerCustomerNameOrCustomerStreetOrBillNo(String customerName, String street, Integer billNo);
	List<Bill> findByCustomerCustomerNameAndCustomerStreetAndBillNo(String customerName, String street, Integer billNo);
	List<Bill> findByCustomerStreet(String street);
	List<Bill> findByBillNo(Integer billNo);
	List<Bill> findByBillSerialAndBillNo(Character billSerial,Integer billNo);

}
