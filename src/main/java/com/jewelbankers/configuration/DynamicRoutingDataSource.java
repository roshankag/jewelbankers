package com.jewelbankers.configuration;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

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
        return contextHolder.get();
    }

    public void setDataSourceKey(String key) {
        contextHolder.set(key);
    }

    public void clearDataSourceKey() {
        contextHolder.remove();
    }

    // Method to add or update a target data source dynamically
    public void addTargetDataSource(String key, DataSource dataSource) {
        this.targetDataSources.put(key, dataSource);
        // Refresh the resolved data sources
        super.setTargetDataSources(this.targetDataSources);
        // Reinitialize to apply the changes
        afterPropertiesSet();
    }

    public void switchSecondaryDataSource() {
        setDataSourceKey("secondary");
    }
}
