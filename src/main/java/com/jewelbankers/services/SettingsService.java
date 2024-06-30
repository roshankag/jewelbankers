package com.jewelbankers.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jewelbankers.entity.Parameters;
import com.jewelbankers.repository.SettingsRepository;

@Service
public class SettingsService {
	@Autowired
	private SettingsRepository settingsRepository;
	public List<Parameters> getParameters() {
		return settingsRepository.findAll();
	}
	
	public Optional<Parameters> findByParamSeq(Long paramSeq) {
		return settingsRepository.findById(paramSeq);
	}

	public Parameters save(Parameters parameterSelect) {
		 return settingsRepository.save(parameterSelect);
	}
	
	

}
