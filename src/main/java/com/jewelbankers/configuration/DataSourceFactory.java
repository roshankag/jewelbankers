package com.jewelbankers.configuration;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import javax.sql.DataSource;

public class DataSourceFactory {

    public static DataSource createDataSource(String url, String username, String password) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }
}
