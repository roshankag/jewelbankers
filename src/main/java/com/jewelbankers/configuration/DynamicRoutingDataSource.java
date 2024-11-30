package com.jewelbankers.configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicRoutingDataSource extends AbstractRoutingDataSource {

    private static final ThreadLocal<AtomicReference<String>> contextHolder = ThreadLocal.withInitial(() -> new AtomicReference<>());

    private final Map<Object, Object> targetDataSources = new ConcurrentHashMap<>();

    @Override
    public void afterPropertiesSet() {
        setTargetDataSources(this.targetDataSources);
        super.afterPropertiesSet();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        String dataSourceKey = contextHolder.get().get();
        return dataSourceKey;
    }

    public void setDataSourceKey(String key) {
        contextHolder.get().set(key);
    }

    public String getDataSourceKey() {
        return contextHolder.get().get();
    }

    public void clearDataSourceKey() {
        contextHolder.remove();
    }

    public synchronized void addTargetDataSource(String key, DataSource dataSource) {
        this.targetDataSources.put(key, dataSource);
        super.setTargetDataSources(this.targetDataSources);
        afterPropertiesSet();
    }

    public void setDefaultDataSource(String key) {
        DataSource dataSource = (DataSource) this.targetDataSources.get(key);
        setDefaultTargetDataSource(dataSource);
    }

    public Map<Object, Object> getDataSource() {
        return this.targetDataSources;
    }
}
