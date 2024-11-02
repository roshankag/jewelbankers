package com.jewelbankers.configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicRoutingDataSource extends AbstractRoutingDataSource {

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    // Store target data sources in a thread-safe map
    private final Map<Object, Object> targetDataSources = new ConcurrentHashMap<>();

    @Override
    public void afterPropertiesSet() {
        // Initialize target data sources
        setTargetDataSources(this.targetDataSources);
        super.afterPropertiesSet();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        // Get the schema or database identifier from context holder
        String dataSourceKey = contextHolder.get();
        System.out.println("Current data source key: " + dataSourceKey); // Debugging
        return dataSourceKey;
    }

    public String getDataSourceKey() {
        return contextHolder.get();
    }
    
    public void setDataSourceKey(String key) {
        contextHolder.set(key);
        System.out.println("Data source key set to: " + key); // Debugging
    }

    public void clearDataSourceKey() {
        contextHolder.remove();
        System.out.println("Data source key cleared."); // Debugging
    }

    // Method to add or update a target data source dynamically based on schema/database
    public void addTargetDataSource(String key, DataSource dataSource) {
        this.targetDataSources.put(key, dataSource);
        // Refresh the resolved data sources
        super.setTargetDataSources(this.targetDataSources);
        // Reinitialize to apply the changes
        afterPropertiesSet();
        System.out.println("Data source added/updated with key: " + key); // Debugging
    }
    
 // Method to set the default data source
    public void setDefaultDataSource(String key) {
        //setDefaultTargetDataSource(defaultDataSource);
        //dynamicRoutingDataSource.setDefaultTargetDataSource(primaryDataSource);    
    	System.out.println("targetDataSources : size : "+this.targetDataSources.size());
    	DataSource dataSource = (DataSource) this.targetDataSources.get(key);
    	setDefaultTargetDataSource(dataSource);
    }

}
