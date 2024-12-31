//package com.jewelbankers.services;
//
//import java.io.FileOutputStream;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.jewelbankers.Utility.SearchSpecification;
//import com.jewelbankers.entity.Barcode;
//import com.jewelbankers.entity.ItemType;
//import com.jewelbankers.repository.BarcodeRepository;
//import com.jewelbankers.repository.ItemTypeRepository;
//
//import jakarta.persistence.EntityNotFoundException;
//
//@Service
//public class BarcodeService {
//
//    @Autowired
//    private BarcodeRepository barcodeRepository;
//    
//    @Autowired
//    private ItemTypeRepository itemTypeRepository;
//    
//    
//    public Barcode createBarcode(Barcode barcode) {
//        // Set creation date for new barcode as a string
//        String formattedDate = LocalDate.now().format(DateTimeFormatter.ISO_DATE);
//        barcode.setBarcodetime(formattedDate);
//
//        // Save barcode to the database
//        return barcodeRepository.save(barcode);
//    }
//
//    public List<Barcode> bulkCreateBarcodes(List<Barcode> barcodes) {
//        String formattedDate = LocalDate.now().format(DateTimeFormatter.ISO_DATE);
//
//        for (Barcode barcode : barcodes) {
//            barcode.setBarcodetime(formattedDate); // Add a creation date as a string for each barcode
//        }
//        return barcodeRepository.saveAll(barcodes);
//    }
//    
// /// Method to print barcode (both JPG and PDF generation)
//    public String printBarcode(String tag) {
//        // Fetch the barcode by tag number
//        Barcode barcode = barcodeRepository.findByTag(tag);
//
//        // Check if the barcode exists
//        if (barcode == null) {
//            return "Barcode not found for the given tag number.";
//        }
//
//        try {
//            // Generate the barcode image (JPG format)
//            byte[] barcodeImageData = BarcodeIMGPrinter.generateBarcodeImage(barcode.getTag());
//
//            // Define the paths for output
//            String pdfFilePath = "barcode-" + barcode.getTag() + ".pdf";
//            String jpgFilePath = "barcode-" + barcode.getTag() + ".jpg";
//
//            // Generate and save the PDF
//            BarcodePDFPrinter.generateBarcodePDF(barcodeImageData, pdfFilePath);
//
//            // Save the JPG image (optional)
//            try (FileOutputStream fos = new FileOutputStream(jpgFilePath)) {
//                fos.write(barcodeImageData);
//            }
//
//            return "Barcode printed successfully in JPG and PDF formats. File paths: " + pdfFilePath + ", " + jpgFilePath;
//        } catch (Exception e) {
//            return "Error while printing barcode: " + e.getMessage();
//        }
//    }
//
//        // Method to print barcodes in bulk
//        public List<String> printBulkBarcodes(List<String> tags) {
//            List<String> responseMessages = new ArrayList<>();
//
//            for (String tag : tags) {
//                try {
//                    // Fetch barcode details from the database
//                    Barcode barcode = barcodeRepository.findByTag(tag);
//
//                    // If barcode is not found, add a failure message to the response
//                    if (barcode == null) {
//                        responseMessages.add("Barcode not found for tag: " + tag);
//                        continue;
//                    }
//
//                    // Generate the barcode image (JPG format) using static method
//                    byte[] barcodeImageData = BarcodeIMGPrinter.generateBarcodeImage(barcode.getTag());
//
//                    // Define file paths for PDF and JPG output
//                    String pdfFilePath = "barcode-" + barcode.getTag() + ".pdf";
//                    String jpgFilePath = "barcode-" + barcode.getTag() + ".jpg";
//
//                    // Generate and save the PDF file using static method
//                    BarcodePDFPrinter.generateBarcodePDF(barcodeImageData, pdfFilePath);
//
//                    // Save the JPG image file
//                    try (FileOutputStream fos = new FileOutputStream(jpgFilePath)) {
//                        fos.write(barcodeImageData);
//                    }
//
//                    // Add success message for the printed barcode
//                    responseMessages.add("Barcode printed successfully for tag: " + tag +
//                            ". File paths: " + pdfFilePath + ", " + jpgFilePath);
//                } catch (Exception e) {
//                    // In case of an error, add the error message to the response
//                    responseMessages.add("Error printing barcode for tag " + tag + ": " + e.getMessage());
//                }
//            }
//
//            return responseMessages;
//        }
//        
//     public List<Barcode> fetchBarcodesByFullsearch(LocalDate startDate, LocalDate endDate, Integer itemTypeNo) {
//            return barcodeRepository.findAll(SearchSpecification.filterByFullSearch(startDate, endDate, itemTypeNo));
//      }
//     
//     public List<Barcode> fetchBarcodesByFilters(LocalDate startDate, LocalDate endDate, Integer itemTypeNo) {
//    	    if (itemTypeNo != null) {
//    	        // If itemTypeNo is provided, filter by itemTypeNo along with date range
//    	        if (startDate != null && endDate != null) {
//    	            return barcodeRepository.findByBarcodeCreatedDateBetweenAndItemMaster_ItemTypeNo(startDate, endDate, itemTypeNo);
//    	        } else if (startDate != null) {
//    	            return barcodeRepository.findByBarcodeCreatedDateGreaterThanEqualAndItemMaster_ItemTypeNo(startDate, itemTypeNo);
//    	        } else if (endDate != null) {
//    	            return barcodeRepository.findByBarcodeCreatedDateLessThanEqualAndItemMaster_ItemTypeNo(endDate, itemTypeNo);
//    	        } else {
//    	            return barcodeRepository.findByItemMaster_ItemTypeNo(itemTypeNo);
//    	        }
//    	    } else {
//    	        // If no itemTypeNo is provided, just filter by date range
//    	        if (startDate != null && endDate != null) {
//    	            return barcodeRepository.findByBarcodeCreatedDateBetween(startDate, endDate);
//    	        } else if (startDate != null) {
//    	            return barcodeRepository.findByBarcodeCreatedDateGreaterThanEqual(startDate);
//    	        } else if (endDate != null) {
//    	            return barcodeRepository.findByBarcodeCreatedDateLessThanEqual(endDate);
//    	        } else {
//    	            return barcodeRepository.findAll();
//    	        }
//    	    }
//    	}
//     
//     public Barcode updateBarcodeByTag(String tag, Barcode updatedBarcode) {
//    	    // Fetch the barcode by tag
//    	    Barcode existingBarcode = barcodeRepository.findByTag(tag);
//    	    if (existingBarcode == null) {
//    	        throw new EntityNotFoundException("Barcode not found with tag: " + tag);
//    	    }
//
//    	    // Update all fields
//    	    existingBarcode.setGrossweight(updatedBarcode.getGrossweight());
//    	    existingBarcode.setNetweight(updatedBarcode.getNetweight());
//    	    existingBarcode.setPurity(updatedBarcode.getPurity());
//    	    existingBarcode.setStoneweight(updatedBarcode.getStoneweight());
//    	    existingBarcode.setMakingcharge(updatedBarcode.getMakingcharge());
//    	    existingBarcode.setMakingpercentage(updatedBarcode.getMakingpercentage());
//    	    existingBarcode.setSize(updatedBarcode.getSize());
//    	    existingBarcode.setHallmarkcharges(updatedBarcode.getHallmarkcharges());
//    	 // Assuming you have an ItemType repository or service to fetch ItemType by its ID
//    	    ItemType itemType = itemTypeRepository.findById(updatedBarcode.getItemType().getItemtypeno())
//    	                                          .orElseThrow(() -> new RuntimeException("ItemType not found"));
//    	    existingBarcode.setItemType(itemType);
//
//    	    // Save updated barcode to the database
//    	    return barcodeRepository.save(existingBarcode);
//    	}
//     
//     public void deleteBarcodeByTag(String tag) {
//    	    // Check if the barcode exists
//    	    Barcode existingBarcode = barcodeRepository.findByTag(tag);
//    	    if (existingBarcode == null) {
//    	        throw new EntityNotFoundException("Barcode not found with tag: " + tag);
//    	    }
//
//    	    // Delete the barcode
//    	    barcodeRepository.delete(existingBarcode);
//    	}
//
//
//
//
//  }
