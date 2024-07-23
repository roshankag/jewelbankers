package com.jewelbankers.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jewelbankers.entity.Settings;

@Repository
public interface SettingsRepository extends JpaRepository<Settings, Long>{
//List<Parameters> findByParamSeq(Long paramSeq);
	List<Settings> findByParamIdContainingOrParamValueContainingOrParamExampleContaining(String paramId, String paramValue, String paramExample);
}
