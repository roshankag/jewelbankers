package com.jewelbankers.Utility;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jewelbankers.entity.Settings;
import com.jewelbankers.repository.SettingsRepository;

@Service
public class SettingsUtillity {
	
	public Map<String, String> convertListToMap(List<Settings> settingsList) {
        Map<String, String> settingsMap = new HashMap<>();
        for (Settings setting : settingsList) {
            settingsMap.put(setting.getParamId(), setting.getParamValue());
        }
        
     // Debug: Print the settingsMap to verify its contents
        System.out.println("Settings Map: " + settingsMap);
        return settingsMap;
    }
	
    public String getShopDetails(Map<String,String> settingsMap) {
    	StringBuffer shopDetailsString = new StringBuffer();
        shopDetailsString.append(settingsMap.get("SHOP_NAME"));
        shopDetailsString.append(settingsMap.get("SHOP_NO"));
        shopDetailsString.append(settingsMap.get("SHOP_STREET"));
        shopDetailsString.append(settingsMap.get("SHOP_AREA"));
        shopDetailsString.append(settingsMap.get("SHOP_CITY"));
        shopDetailsString.append(settingsMap.get("SHOP_PINCODE"));
        shopDetailsString.append(settingsMap.get("SHOP_STATE"));
		return shopDetailsString.toString();    
		}
    
    public String getAuctionDescription(Map<String, String> settingsMap) {
        String auctionDetails = settingsMap.get("AUCTION_DETAILS");
        if (auctionDetails != null && !auctionDetails.isEmpty()) {
            return formatAuctionDescriptionForThreeLines(auctionDetails, 70); // Wrap lines at 70 characters for A4 size
        }
        return null;
    }

    // Helper method to format auction description into three lines for A4 paper layout
    private String formatAuctionDescriptionForThreeLines(String text, int maxLineLength) {
        StringBuilder formattedText = new StringBuilder();
        String[] words = text.split(" ");
        int lineLength = 0;
        int lineCount = 1;  // To keep track of line numbers

        // Distribute text into 3 lines
        StringBuilder line1 = new StringBuilder();
        StringBuilder line2 = new StringBuilder();
        StringBuilder line3 = new StringBuilder();

        // Split the text into 3 parts
        int wordsPerLine = words.length / 3;
        int wordCounter = 0;

        for (String word : words) {
            if (lineCount == 1) {
                if (line1.length() + word.length() + 1 <= maxLineLength && wordCounter < wordsPerLine) {
                    line1.append(word).append(" ");
                    wordCounter++;
                } else {
                    lineCount = 2; // Move to the second line when first is filled
                    wordCounter = 0;
                    line2.append(word).append(" ");
                }
            } else if (lineCount == 2) {
                if (line2.length() + word.length() + 1 <= maxLineLength && wordCounter < wordsPerLine) {
                    line2.append(word).append(" ");
                    wordCounter++;
                } else {
                    lineCount = 3; // Move to the third line when second is filled
                    wordCounter = 0;
                    line3.append(word).append(" ");
                }
            } else if (lineCount == 3) {
                line3.append(word).append(" "); // Add remaining words to the third line
            }
        }

        // Combine all lines
        formattedText.append(line1.toString().trim()).append("\n")
                     .append(line2.toString().trim()).append("\n")
                     .append(line3.toString().trim());

        return formattedText.toString().trim(); // Return formatted text with trimmed spaces
    }


    // Method to align text (e.g., center or justify lines)
    private String formatLine(String line) {
        return String.format("%-70s", line);  // This left-aligns the text and ensures it fits within the max line length
    }


    public String getPledgeRules(Map<String,String> settingsMap) {
    	return settingsMap.get("PLEDGE_RULES");
    }
    
    public String getPhotoFolder(Map<String,String> settingsMap) {
    	return settingsMap.get("PHOTO_FOLDER");
    }
    
    public BigDecimal getCgst(Map<String, String> settingsMap) {
        String cgstValue = settingsMap.get("CGST");
        return new BigDecimal(cgstValue);
    }

    public BigDecimal getSgst(Map<String, String> settingsMap) {
        String sgstValue = settingsMap.get("SGST");
        return new BigDecimal(sgstValue);
    }
    
    public String getGstNumber(Map<String, String> settingsMap) {
        return settingsMap.get("GST_Number");
    }
    
    public String getGoldRate(Map<String, String> settingsMap) {
        return settingsMap.get("GOLD_RATE");
    }
    
    public String getSilverRate(Map<String, String> settingsMap) {
        return settingsMap.get("SILVER_RATE");
    }
    
    public String getDiamondRate(Map<String, String> settingsMap) {
        return settingsMap.get("DIAMOND_RATE");
    }
    
    public String getExcelPassword(Map<String, String> settingsMap) {
        return settingsMap.get("EXCEL_PASSWORD");
    }
    
    public String getLicenceNo(Map<String, String> settingsMap) {
        return settingsMap.get("LICENCE_NO");
    }
    
 // Fetch rate based on itemtypeno (1 for Gold, 2 for Silver, etc.)
    public BigDecimal getRateByItemType(Map<String, String> settingsMap, Integer itemtypeno) {
        String rateKey = getRateKeyByItemType(itemtypeno);
        String rateValue = settingsMap.get(rateKey);

        if (rateValue != null) {
            return new BigDecimal(rateValue);
        } else {
            throw new RuntimeException("Rate not found for item type: " + itemtypeno);
        }
    }

    // Helper method to map itemtypeno to settings keys
    private String getRateKeyByItemType(Integer itemtypeno) {
        switch (itemtypeno) {
            case 1:
                return "GOLD_RATE";
            case 2:
                return "SILVER_RATE";
            case 3:
                return "DIAMOND_RATE";
            default:
                throw new IllegalArgumentException("Invalid item type number: " + itemtypeno);
        }
    }

    
}
