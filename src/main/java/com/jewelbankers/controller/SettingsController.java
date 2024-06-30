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

import com.jewelbankers.entity.Parameters;
import com.jewelbankers.services.SettingsService;


@RestController
@RequestMapping("/settings")
public class SettingsController {
	
	@Autowired
	private SettingsService settingsService;
	
	@GetMapping
	public List<Parameters> getParameters() {
		return settingsService.getParameters();
	}
	
	@PutMapping("/{id}")
    public ResponseEntity<Parameters> updateParameter(@PathVariable("id") Long paramSeq, @RequestBody Parameters inputParameter) {
		Optional<Parameters> existingParameter = settingsService.findByParamSeq(paramSeq);
		
        if (!existingParameter.isPresent()) {
        	return ResponseEntity.notFound().build();        
        	}

        Parameters parameterSelect= existingParameter.get();
        
        if (inputParameter.getParamSeq() != null) parameterSelect.setParamSeq(inputParameter.getParamSeq());
        if (inputParameter.getParamId() != null) parameterSelect.setParamId(inputParameter.getParamId());
        if (inputParameter.getParamValue() != null) parameterSelect.setParamValue(inputParameter.getParamValue());
        if (inputParameter.getParamExample()!= null) parameterSelect.setParamExample(inputParameter.getParamExample());
        
        
        Parameters updateParameters = settingsService.save(parameterSelect);
        return ResponseEntity.ok(updateParameters);
}
}