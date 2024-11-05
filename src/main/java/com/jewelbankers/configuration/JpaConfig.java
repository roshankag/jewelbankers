////package com.jewelbankers.configuration;
////
////import javax.sql.DataSource;
////
////import org.springframework.boot.jdbc.DataSourceBuilder;
////import org.springframework.context.annotation.Bean;
////import org.springframework.context.annotation.Configuration;
////import org.springframework.orm.jpa.JpaTransactionManager;
////import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
////import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
////import org.springframework.transaction.annotation.EnableTransactionManagement;
//////@Configuration
//////@EnableTransactionManagement
////public class JpaConfig {
////
////	/*
////	 * @Bean public LocalContainerEntityManagerFactoryBean
////	 * entityManagerFactory(DataSource primaryDataSource) {
////	 * LocalContainerEntityManagerFactoryBean em = new
////	 * LocalContainerEntityManagerFactoryBean();
////	 * em.setDataSource(primaryDataSource);
////	 * em.setPackagesToScan("com.jewelbankers.entity"); // Replace with your entity
////	 * package em.setJpaVendorAdapter(new HibernateJpaVendorAdapter()); return em; }
////	 * 
////	 * @Bean public JpaTransactionManager
////	 * transactionManager(LocalContainerEntityManagerFactoryBean
////	 * entityManagerFactory) { return new
////	 * JpaTransactionManager(entityManagerFactory.getObject()); }
////	 */
////
////	
////	//  @Bean	  
////	// @Qualifier("primary") 
////	  //@Primary
////	 public DataSource dataSource1() { return
////	 DataSourceBuilder.create() .url("jdbc:mysql://localhost:3306/krishnag")
////	  .username("root") .password("admin") .build(); }
////	 
////
////	//@Bean
////	//@Qualifier("secondary")
////	public DataSource dataSource2() {
////		return DataSourceBuilder.create().url("jdbc:mysql://localhost:3306/roshankag2003").username("root")
////				.password("admin").build();
////	}
////}
//
//
//package com.jewelbankers.configuration;
//
//import javax.sql.DataSource;
//
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//@Configuration
//@EnableTransactionManagement
//public class JpaConfig {
//
//    @Bean
//    public DataSource dataSource() {
//        return DataSourceBuilder.create()
//                .url("jdbc:mysql://localhost:3306/ambikam") // Update with your database URL
//                .username("root") // Update with your username
//                .password("admin") // Update with your password
//                .build();
//    }
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
//        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//        em.setDataSource(dataSource);
//        em.setPackagesToScan("com.jewelbankers.entity"); // Replace with your entity package
//        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
//        return em;
//    }
//
//    @Bean
//    public JpaTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactory) {
//        return new JpaTransactionManager(entityManagerFactory.getObject());
//    }
//}
//
//



package com.jewelbankers.configuration;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ConfigurationProperties(prefix = "spring.jpa")
@EnableTransactionManagement
public class JpaConfig {
	
	@Autowired
	@Lazy
	//private DynamicRoutingDataSource dynamicRoutingDataSource;
	private DynamicRoutingDataSource dynamicRoutingDataSource;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dynamicRoutingDataSource);
        em.setPackagesToScan("com.jewelbankers.entity"); // Your entity package
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        return em;
    }

    @Bean
    public PlatformTransactionManager transactionManager(@Qualifier("entityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory.getObject());
    }

}

