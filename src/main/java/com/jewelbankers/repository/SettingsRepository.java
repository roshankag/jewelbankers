package com.jewelbankers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jewelbankers.entity.Parameters;

@Repository
public interface SettingsRepository extends JpaRepository<Parameters, Long>{
//List<Parameters> findByParamSeq(Long paramSeq);
}
