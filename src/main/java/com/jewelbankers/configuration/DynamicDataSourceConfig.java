package com.jewelbankers.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

//@Configuration
public class DynamicDataSourceConfig {

   
	//@Bean
//    public DataSource dynamicDataSource() {
//        DynamicDataSource dynamicDataSource = new DynamicDataSource();
//        Map<Object, Object> targetDataSources = new HashMap<>();
//        targetDataSources.put("dataSource1", dataSource1());
//        targetDataSources.put("dataSource2", dataSource2());
//
//        dynamicDataSource.setTargetDataSources(targetDataSources);
//        dynamicDataSource.setDefaultTargetDataSource(dataSource1());
//        return dynamicDataSource;
//    }

     
}
