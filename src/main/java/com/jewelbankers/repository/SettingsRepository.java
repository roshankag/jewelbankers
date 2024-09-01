package com.jewelbankers.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jewelbankers.entity.Settings;

@Repository
public interface SettingsRepository extends JpaRepository<Settings, Long>{
//	Optional<Settings> findByParamName(String paramName);
    Optional<Settings> findByParamSeq(Long paramSeq);
	Optional<Settings> findByParamId(String paramId);
	
	List<Settings> findByParamIdContainingOrParamValueContainingOrParamExampleContaining(String paramId, String paramValue, String paramExample);
}
