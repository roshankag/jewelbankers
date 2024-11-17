package com.jewelbankers.configuration;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.jewelbankers.entity.User;
import com.jewelbankers.services.UserDetailsServiceImpl;

@Component
public class DataSourceInitializer implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private DynamicRoutingDataSource dynamicRoutingDataSource;

    @Value("${datasource.primary.username}")
    private String primaryDataSourceUsername;

    @Value("${datasource.primary.password}")
    private String primaryDataSourcePassword;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        List<User> users = userDetailsServiceImpl.allUsers();
        for (User user : users) {
            String url = "jdbc:mysql://localhost:3306/" + user.getUserDatabaseName() + "?allowPublicKeyRetrieval=true&useSSL=false";
            System.out.println("Data source URL: " + url);
            DataSource newDataSource = DataSourceFactory.createDataSource(url, primaryDataSourceUsername, primaryDataSourcePassword);
            dynamicRoutingDataSource.addTargetDataSource(user.getUserDatabaseName(), newDataSource);
        }
    }
}
