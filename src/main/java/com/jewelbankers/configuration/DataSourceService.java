package com.jewelbankers.configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class DataSourceService {

    // private final DynamicRoutingDataSource dynamicRoutingDataSource;
    private final Map<String, DataSource> userDataSources = new ConcurrentHashMap<>();
    
    public Map<String, DataSource> getUserDataSources() {
		return userDataSources;
	}

	@Autowired
    @Lazy
    private DynamicRoutingDataSource dynamicRoutingDataSource;

//    @Autowired
//    public DataSourceService(@Qualifier("dynamicRoutingDataSource") DynamicRoutingDataSource dynamicRoutingDataSource) {
//        this.dynamicRoutingDataSource = dynamicRoutingDataSource;
//    }

    /**
     * Switch the data source based on userId or databaseName.
     * If the data source does not already exist for the user, it is created and added.
     */
    public void switchDataSource(String databaseName) {
    	
    	//dynamicRoutingDataSource.setDataSourceKey(databaseName);
    	//dynamicRoutingDataSource.setDefaultDataSource(databaseName);
        // Perform database operations here using the specified dbName
        dynamicRoutingDataSource.clearDataSourceKey();
        
    	dynamicRoutingDataSource.setDataSourceKey(databaseName);
    	dynamicRoutingDataSource.setDefaultDataSource(databaseName);
    	
    	//dynamicRoutingDataSource.setDataSourceKey("secondary");
    	//dynamicRoutingDataSource.setDefaultDataSource("secondary");
    	    }

    public int getDataSourceCount() {
        return userDataSources.size();
    }
    
    public void checkDataSources() {
       
    }
    
   
    
    /**
     * Create a new data source based on the database name or user ID.
     */
//    private DataSource createDataSourceForUser(String databaseName) {
//        // Example code for creating a DataSource dynamically
//        return DataSourceBuilder.create()
//                .url("jdbc:mysql://localhost:3306/" + databaseName)
//                .username("root")
//                .password("admin")
//                .build();
//    }

    /**
     * Clear the current data source after the user logs out or if necessary.
     */
    public void clearDataSource() {
        dynamicRoutingDataSource.clearDataSourceKey();
    }
}