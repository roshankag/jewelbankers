package com.jewelbankers.configuration;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

@Component
public class DataSourceFactory {
    public DataSource createDataSource(String dbName) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver"); // Set your driver class
        dataSource.setUrl("jdbc:mysql://localhost:3306/" + dbName); // Set the URL dynamically
        dataSource.setUsername("root"); // Update with your database username
        dataSource.setPassword("admin"); // Update with your database password
        return dataSource;
    }
}
