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
     * Only switches if the datasource key is already registered.
     */
    public void switchDataSource(String databaseName) {
        lock.lock();  // acquire the lock
        try {
            dynamicRoutingDataSource.clearDataSourceKey();
            dynamicRoutingDataSource.setDataSourceKey(databaseName);
            // NOTE: Do NOT call setDefaultDataSource here — it would corrupt the
            // global default for all threads. The ThreadLocal key is sufficient.
        } finally {
            lock.unlock();  // always release the lock
        }
    }

    /**
     * Returns true if the given database name is registered as a datasource.
     */
    public boolean isDataSourceRegistered(String databaseName) {
        return dynamicRoutingDataSource.getDataSource().containsKey(databaseName);
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