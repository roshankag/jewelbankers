package com.jewelbankers.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jewelbankers.entity.Settings;
import com.jewelbankers.repository.SettingsRepository;

@Service
public class SettingsService {
	@Autowired
	private SettingsRepository settingsRepository;
	public List<Settings> getSettings() {
		return settingsRepository.findAll();
	}
	
	public Optional<Settings> findByParamSeq(Long paramSeq) {
		return settingsRepository.findById(paramSeq);
	}

	public Settings save(Settings parameterSelect) {
		 return settingsRepository.save(parameterSelect);
	}
	
	

}
