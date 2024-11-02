//package com.jewelbankers.configuration;
//
//
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.sql.DataSource;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//
//@Configuration
//public class DataSourceConfig {
//
//    @Value("${datasource.primary.url}")
//    private String primaryDataSourceUrl;
//
//    @Value("${datasource.primary.username}")
//   private String primaryDataSourceUsername;
//
//    @Value("${datasource.primary.password}")
//    private String primaryDataSourcePassword;
//
//    @Value("${datasource.secondary.url}")
//    private String secondaryDataSourceUrl;
//
//    @Value("${datasource.secondary.username}")
//    private String secondaryDataSourceUsername;
//
//    @Value("${datasource.secondary.password}")
//    private String secondaryDataSourcePassword;
//
//    @Bean
//    public DynamicRoutingDataSource dynamicRoutingDataSource() {
//        DynamicRoutingDataSource dynamicRoutingDataSource = new DynamicRoutingDataSource();
//
//        // Configure the default and initial data sources
//        DataSource primaryDataSource = DataSourceFactory.createDataSource(
//            primaryDataSourceUrl, primaryDataSourceUsername, primaryDataSourcePassword);
//        DataSource secondaryDataSource = DataSourceFactory.createDataSource(
//            secondaryDataSourceUrl, secondaryDataSourceUsername, secondaryDataSourcePassword);
//
//        dynamicRoutingDataSource.setDefaultTargetDataSource(primaryDataSource);
//
//        Map<Object, Object> targetDataSources = new HashMap<>();
//        targetDataSources.put("primary", primaryDataSource);
//        targetDataSources.put("secondary", secondaryDataSource);
//
//        dynamicRoutingDataSource.setTargetDataSources(targetDataSources);
//        dynamicRoutingDataSource.afterPropertiesSet();
//
//        return dynamicRoutingDataSource;
//    }
//    
//
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DynamicRoutingDataSource dynamicRoutingDataSource) {
//        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//        em.setDataSource(dynamicRoutingDataSource); // Set the dynamic routing data source here
//        em.setPackagesToScan("com.jewelbankers.entity"); // Replace with your entity package
//        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
//
//        return em;
//    }
//    @Bean
//    public JpaTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactory) {
//        return new JpaTransactionManager(entityManagerFactory.getObject());
//    }	
//
//}


package com.jewelbankers.configuration;

import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DataSourceConfig {

	
	@Autowired
	@Lazy
	DynamicRoutingDataSource dynamicRoutingDataSource;
	
	@Autowired
	DataSourceService dataSourceService;
	
	@Autowired
	DataSourceFactory dataSourceFactory;
	    @Bean
	    public JdbcTemplate jdbcTemplate() { 
	        return new JdbcTemplate(dynamicRoutingDataSource);
	    }
	    
//	    @Autowired
//	    DynamicRoutingDataSource dynamicRoutingDataSource;
	  


//	    @Bean
//	    @Qualifier("secondaryDataSource")
//	    public DataSource secondaryDataSource() {
//	        return DataSourceBuilder.create()
//	                .url("jdbc:mysql://localhost:3306/ambikam") // Update with your secondary database URL
//	                .username("root") // Update with your username
//	                .password("admin") // Update with your password
//	                .build();
//	    }
	    
//	    @Bean
//	    @Qualifier("primaryDataSource")
//	    public DataSource primaryDataSource() {
//	    	return dataSourceFactory.createDataSource("jewelbankers_users");
//	        return DataSourceBuilder.create()
//	                .url("jdbc:mysql://localhost:3306/jewelbankers_users") // Update with your database URL
//	                .username("root") // Update with your username
//	                .password("admin") // Update with your password
//	                .build();
//	    }
	
//	    @Bean
//	    public DynamicRoutingDataSource dynamicRoutingDataSource(
//	            @Qualifier("primaryDataSource") DataSource primaryDataSource,
//	            @Qualifier("secondaryDataSource") DataSource secondaryDataSource) {
//        // Set up the dynamic data source with the default data source
//        DynamicRoutingDataSource dynamicRoutingDataSource = new DynamicRoutingDataSource();
//        dynamicRoutingDataSource.addTargetDataSource("primary", primaryDataSource);
//        dynamicRoutingDataSource.addTargetDataSource("secondary", secondaryDataSource);
//        dynamicRoutingDataSource.setDefaultTargetDataSource(primaryDataSource);    
//
//       // dynamicRoutingDataSource.setDefaultTargetDataSource(secondaryDataSource);
////        dynamicRoutingDataSource.setDefaultTargetDataSource(primaryDataSource);    
//
////dynamicRoutingDataSource.addTargetDataSource("jewelbankers_users", primaryDataSource);
//        //dynamicRoutingDataSource.setDataSourceKey("jewelbankers_users");
//        //dataSourceService.getUserDataSources().put("jewelbankers_users", primaryDataSource());
//        //System.out.println("current data source : "+dynamicRoutingDataSource.getDataSourceKey());
//        
//        return dynamicRoutingDataSource;
//    }
    
	    @Bean
	    public DynamicRoutingDataSource dynamicRoutingDataSource() {
	        DynamicRoutingDataSource dynamicRoutingDataSource = new DynamicRoutingDataSource();
	        
	        // Loop through your database names and create data sources dynamically
	        for (String dbName : getDatabaseNames()) { // Assume this method returns a list of your database names
	            DataSource dataSource = dataSourceFactory.createDataSource(dbName);
	            dynamicRoutingDataSource.addTargetDataSource(dbName, dataSource);
	        }
	        
	        // Optionally set a default data source
	        dynamicRoutingDataSource.setDefaultTargetDataSource(dataSourceFactory.createDataSource("jewelbankers_users"));

	        return dynamicRoutingDataSource;
	    }

	    private List<String> getDatabaseNames() {
	        // Retrieve your database names dynamically from a config, database, etc.
	        return Arrays.asList("jewelbankers_users", "ambikam", "db3");
	    }

}





