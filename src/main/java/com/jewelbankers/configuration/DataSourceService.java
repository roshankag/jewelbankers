package com.jewelbankers.configuration;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataSourceService {

    @Autowired
    private DynamicRoutingDataSource dynamicRoutingDataSource;

    public void updateSecondaryDataSource(String url, String username, String password) {
        DataSource newDataSource = DataSourceFactory.createDataSource(url, username, password);
        dynamicRoutingDataSource.addTargetDataSource("secondary", newDataSource);
    }
    
    public void clearDataSource() {
    	dynamicRoutingDataSource.clearDataSourceKey();
    }
    
    public void switchSecondaryDataSource() {
    	dynamicRoutingDataSource.switchSecondaryDataSource();
    }
}



