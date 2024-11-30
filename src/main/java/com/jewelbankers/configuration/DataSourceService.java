package com.jewelbankers.configuration;

import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class DataSourceService {

	private final ReentrantLock lock = new ReentrantLock();

	@Autowired
    @Lazy
    private DynamicRoutingDataSource dynamicRoutingDataSource;



    /**
     * Switch the data source based on userId or databaseName.
     * If the data source does not already exist for the user, it is created and added.
     */
    public void switchDataSource(String databaseName) {
        lock.lock();  // acquire the lock
        
        try {
            dynamicRoutingDataSource.clearDataSourceKey();
            dynamicRoutingDataSource.setDataSourceKey(databaseName);
            dynamicRoutingDataSource.setDefaultDataSource(databaseName);
        } finally {
            lock.unlock();  // always release the lock
        }
    }


    public void checkDataSources() {
       
    }
  

    /**
     * Clear the current data source after the user logs out or if necessary.
     */
    public void clearDataSource() {
        dynamicRoutingDataSource.clearDataSourceKey();
    }
}