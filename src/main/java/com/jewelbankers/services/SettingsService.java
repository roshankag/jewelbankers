package com.jewelbankers.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jewelbankers.entity.Settings;
import com.jewelbankers.repository.SettingsRepository;

import jakarta.transaction.Transactional;

@Service
public class SettingsService {
	@Autowired
	private SettingsRepository settingsRepository;
	public List<Settings> getSettings() {
		return settingsRepository.findAll();
	}
	
	
	
	@Transactional
    public List<Settings> updateAllSettings(List<Settings> settingsList) {
        List<Settings> updatedSettings = new ArrayList<>();
        for (Settings settings : settingsList) {
            // Perform any necessary updates
            updatedSettings.add(settingsRepository.save(settings));
        }
        return updatedSettings;
    }
	
	public String getDriveLink() {
	    Optional<Settings> driveLinkSetting = settingsRepository.findByParamId("DRIVE_LINK");
	    return driveLinkSetting.map(Settings::getParamValue)
	                           .orElse("Default Drive Link if not found");
	}
	
	public String getCustomerPhotoDirectory() {
        Long paramSeq = 42L; // Assuming 1L is the paramSeq for the photo directory
        Optional<Settings> settingOpt = settingsRepository.findByParamSeq(paramSeq);
        if (settingOpt.isPresent()) {
            return settingOpt.get().getParamValue();
        } else {
            throw new RuntimeException("Photo directory setting not found");
        }
    }


    public String getParamValueByParamId(String paramId) {
        Optional<Settings> setting = settingsRepository.findByParamId(paramId);
        return setting.map(Settings::getParamValue)
                .orElseThrow(() -> new RuntimeException("Parameter not found in settings with paramId: " + paramId));
    }
	
	public Optional<Settings> findByParamSeq(Long paramSeq) {
		return settingsRepository.findById(paramSeq);
	}
	
	 // Fetch shop details using paramSeq
    public Map<String, String> getShopDetails() {
        Map<String, String> shopDetails = new HashMap<>();

        shopDetails.put("SHOP_NAME", findByParamSeq(21L).map(Settings::getParamValue).orElse(""));
        shopDetails.put("SHOP_NO", findByParamSeq(22L).map(Settings::getParamValue).orElse(""));
        shopDetails.put("SHOP_STREET", findByParamSeq(23L).map(Settings::getParamValue).orElse(""));
        shopDetails.put("SHOP_AREA", findByParamSeq(24L).map(Settings::getParamValue).orElse(""));
        shopDetails.put("SHOP_CITY", findByParamSeq(25L).map(Settings::getParamValue).orElse(""));
        shopDetails.put("SHOP_PINCODE", findByParamSeq(26L).map(Settings::getParamValue).orElse(""));
        shopDetails.put("SHOP_STATE", findByParamSeq(27L).map(Settings::getParamValue).orElse(""));

        return shopDetails;
    }

	public Settings save(Settings parameterSelect) {
		 return settingsRepository.save(parameterSelect);
	}
	public List<Settings> searchSettings(String query) {
        return settingsRepository.findByParamIdContainingOrParamValueContainingOrParamExampleContaining(query, query, query);
    }
	

}
