package com.jewelbankers.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jewelbankers.entity.Settings;
import com.jewelbankers.services.SettingsService;


@RestController
@RequestMapping("/settings")
public class SettingsController {
	
	@Autowired
	private SettingsService settingsService;
	
	@GetMapping
	public List<Settings> getSettings() {
		return settingsService.getSettings();
	}
	
	@PutMapping("/{id}")
    public ResponseEntity<Settings> updateSettings(@PathVariable("id") Long paramSeq, @RequestBody Settings inputSettings) {
		Optional<Settings> existingSettings = settingsService.findByParamSeq(paramSeq);
		
        if (!existingSettings.isPresent()) {
        	return ResponseEntity.notFound().build();        
        	}

        Settings settingsSelect= existingSettings.get();
        
        if (inputSettings.getParamSeq() != null) settingsSelect.setParamSeq(inputSettings.getParamSeq());
        if (inputSettings.getParamId() != null) settingsSelect.setParamId(inputSettings.getParamId());
        if (inputSettings.getParamValue() != null) settingsSelect.setParamValue(inputSettings.getParamValue());
        if (inputSettings.getParamExample()!= null) settingsSelect.setParamExample(inputSettings.getParamExample());
        
        
        Settings updateSettings = settingsService.save(settingsSelect);
        return ResponseEntity.ok(updateSettings);
}
}