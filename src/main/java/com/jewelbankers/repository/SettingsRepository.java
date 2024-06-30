package com.jewelbankers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jewelbankers.entity.Settings;

@Repository
public interface SettingsRepository extends JpaRepository<Settings, Long>{
//List<Parameters> findByParamSeq(Long paramSeq);
}
