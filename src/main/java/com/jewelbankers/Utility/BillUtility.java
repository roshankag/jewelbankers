package com.jewelbankers.Utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BillUtility {
	public static boolean ValidateBillNo(String searchValue) {
        // Sample input
      //  String input = "b9089";
        
        // Define the regex pattern
        String regex = "^[a-zA-Z]\\d{1,5}$";
        
        // Compile the pattern
        Pattern pattern = Pattern.compile(regex);
        
        // Match the input string against the pattern
        Matcher matcher = pattern.matcher(searchValue);
        
//        // Check if the input matches the pattern
     if (matcher.matches()) {
            System.out.println("The input matches the pattern.");
        } else {
            System.out.println("The input does not match the pattern.");
        }
		return matcher.matches();
    }
	public static void main(String[] args) {
        // Sample input
        String input = "b9089";
        
        // Define the regex pattern
        String regex = "[a-zA-Z]\\d{4,5}";
        
        // Compile the pattern
        Pattern pattern = Pattern.compile(regex);
        
        // Match the input string against the pattern
        Matcher matcher = pattern.matcher(input);
        
        // Find the first match
        if (matcher.find()) {
            // Extract the first matching value
            String firstMatch = matcher.group();
            System.out.println("First match: " + firstMatch);
        } else {
            System.out.println("No match found.");
        }
    }
}

