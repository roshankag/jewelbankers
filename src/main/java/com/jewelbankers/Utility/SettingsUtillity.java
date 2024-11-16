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
    
    public String getAuctionDescription(Map<String,String> settingsMap) {
    	return settingsMap.get("AUCTION_DETAILS");
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
    
    
    
//    public String getExcelPassword(Map<String, String> settingsMap) {
//        // Fetch the base password number from the settings (e.g., "EXCEL_PASSWORD" holds the base value as a string)
//        String basePasswordValue = settingsMap.get("EXCEL_PASSWORD");
//        if (basePasswordValue != null) {
//            try {
//                // Convert the base password value to an integer and add 100
//                int basePassword = Integer.parseInt(basePasswordValue);
//                return String.valueOf(basePassword + 100);
//            } catch (NumberFormatException e) {
//                throw new RuntimeException("Invalid password value in settings");
//            }
//        }
//        return "defaultPassword"; // Fallback password in case the setting is not found
//    }
    
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
