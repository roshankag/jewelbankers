package com.jewelbankers.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jewelbankers.entity.Bill;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long>,  JpaSpecificationExecutor<Bill> {

	List<Bill> findByBillSequence(Long billSequence);
	//List<Bill> findByCustomer(Customer customer);
	List<Bill> findByCustomerCustomerNameOrCustomerStreetOrBillNo(String customerName, String street, Integer billNo);
	List<Bill> findByCustomerCustomerNameAndCustomerStreetAndBillNo(String customerName, String street, Integer billNo);
	List<Bill> findByCustomerStreet(String street);
	List<Bill> findByBillNo(Integer billNo);
	List<Bill> findByCustomerCustomerName(String customerName);
	List<Bill> findByBillSerialAndBillNo(Character billSerial,Integer billNo);
	List<Bill> findByBillDateBetween(LocalDate fromDate, LocalDate endDate);
	List<Bill> findByRedemptionStatus(Character redemptionStatus);
	List<Bill> findByProductTypeNo(Long productTypeNo);
	
	// Find bills by Bill Serial and Bill No, sorted by 'billSeq' in descending order
    List<Bill> findByBillSerialAndBillNoOrderByBillSequenceDesc(Character billSerial, Integer billNo);
    
    // Find bills by Customer Name, sorted by 'billSeq' in descending order
    List<Bill> findByCustomerCustomerNameOrderByBillSequenceDesc(String customerName);
	
//	List<Bill> findByRedeemBillSerialAndBillNo(Character billSerial, Integer billNo);
	
	 @Query(value="SELECT COALESCE(MAX(bill_No), 0) FROM bill_header WHERE BILL_SERIAL =  (SELECT PARAM_VALUE FROM parameters WHERE param_id='CURRENT_SERIAL')",nativeQuery = true)
	    Integer findCurrentBillNo();

	 @Query(value="SELECT COALESCE(MAX(BILL_REDEM_NO), 0) FROM bill_header WHERE BILL_REDEM_SERIAL =  (SELECT PARAM_VALUE FROM parameters WHERE param_id='REDEM_CURRENT_SERIAL')",nativeQuery = true)
	    Integer findCurrentBillRedemNo();
	    
	   


}
