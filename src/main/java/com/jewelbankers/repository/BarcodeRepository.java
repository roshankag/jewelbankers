//package com.jewelbankers.repository;
//
//import java.time.LocalDate;
//import java.util.List;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
//import org.springframework.stereotype.Repository;
//
//import com.jewelbankers.entity.Barcode;
//
////@Repository
//public interface BarcodeRepository extends JpaRepository<Barcode, Long>, JpaSpecificationExecutor<Barcode>  {
//    // Custom queries if needed can be added later
//	 Barcode findByTag(String tag);
//	 
//	// Query method to find barcodes by barcodeCreatedDate range and item type number
//	    List<Barcode> findByBarcodeCreatedDateBetweenAndItemMaster_ItemTypeNo(LocalDate startDate, LocalDate endDate, Integer itemTypeNo);
//
//	    // Query method to find barcodes by barcodeCreatedDate and item type number
//	    List<Barcode> findByBarcodeCreatedDateGreaterThanEqualAndItemMaster_ItemTypeNo(LocalDate startDate, Integer itemTypeNo);
//
//	    // Query method to find barcodes by barcodeCreatedDate and item type number
//	    List<Barcode> findByBarcodeCreatedDateLessThanEqualAndItemMaster_ItemTypeNo(LocalDate endDate, Integer itemTypeNo);
//
//	    // Query method to find barcodes by item type number
//	    List<Barcode> findByItemMaster_ItemTypeNo(Integer itemTypeNo);
//
//	    // Query method to find barcodes by barcodeCreatedDate range
//	    List<Barcode> findByBarcodeCreatedDateBetween(LocalDate startDate, LocalDate endDate);
//
//	    // Query method to find barcodes by barcodeCreatedDate
//	    List<Barcode> findByBarcodeCreatedDateGreaterThanEqual(LocalDate startDate);
//
//	    // Query method to find barcodes by barcodeCreatedDate
//	    List<Barcode> findByBarcodeCreatedDateLessThanEqual(LocalDate endDate);
//
//	    // Fetch all barcodes
//	    List<Barcode> findAll();
//}
