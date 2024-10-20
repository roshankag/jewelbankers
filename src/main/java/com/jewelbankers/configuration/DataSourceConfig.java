package com.jewelbankers.configuration;


import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
public class DataSourceConfig {

    @Value("${datasource.primary.url}")
    private String primaryDataSourceUrl;

    @Value("${datasource.primary.username}")
    private String primaryDataSourceUsername;

    @Value("${datasource.primary.password}")
    private String primaryDataSourcePassword;

    @Value("${datasource.secondary.url}")
    private String secondaryDataSourceUrl;

    @Value("${datasource.secondary.username}")
    private String secondaryDataSourceUsername;

    @Value("${datasource.secondary.password}")
    private String secondaryDataSourcePassword;

    @Bean
    public DynamicRoutingDataSource dynamicRoutingDataSource() {
        DynamicRoutingDataSource dynamicRoutingDataSource = new DynamicRoutingDataSource();

        // Configure the default and initial data sources
        DataSource primaryDataSource = DataSourceFactory.createDataSource(
            primaryDataSourceUrl, primaryDataSourceUsername, primaryDataSourcePassword);
        DataSource secondaryDataSource = DataSourceFactory.createDataSource(
            secondaryDataSourceUrl, secondaryDataSourceUsername, secondaryDataSourcePassword);

        dynamicRoutingDataSource.setDefaultTargetDataSource(primaryDataSource);

        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("primary", primaryDataSource);
        targetDataSources.put("secondary", secondaryDataSource);

        dynamicRoutingDataSource.setTargetDataSources(targetDataSources);
        dynamicRoutingDataSource.afterPropertiesSet();

        return dynamicRoutingDataSource;
    }
    

    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DynamicRoutingDataSource dynamicRoutingDataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dynamicRoutingDataSource); // Set the dynamic routing data source here
        em.setPackagesToScan("com.jewelbankers.entity"); // Replace with your entity package
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        return em;
    }
    @Bean
    public JpaTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory.getObject());
    }	

}
