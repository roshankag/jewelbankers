//package com.jewelbankers.Utility;
//
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class JewelleryUtility {
//	
//	public static boolean ValidateInvoiceNo(String searchValue) {
//        // Define the regex pattern for invoice numbers (similar to BillUtility)
//        String regex = "^[a-zA-Z]\\d{1,5}$";
//
//        // Compile the pattern
//        Pattern pattern = Pattern.compile(regex);
//
//        // Match the input string against the pattern
//        Matcher matcher = pattern.matcher(searchValue);
//
//        // Log the validation result
//        if (matcher.matches()) {
//            System.out.println("The input matches the pattern.");
//        } else {
//            System.out.println("The input does not match the pattern.");
//        }
//        return matcher.matches();
//    }
//
//}
