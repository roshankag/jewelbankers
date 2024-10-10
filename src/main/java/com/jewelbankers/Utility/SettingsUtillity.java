package com.jewelbankers.Utility;

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
    
    
}
