package com.jewelbankers.configuration;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
//@Configuration
//@EnableTransactionManagement
public class JpaConfig {

	/*
	 * @Bean public LocalContainerEntityManagerFactoryBean
	 * entityManagerFactory(DataSource primaryDataSource) {
	 * LocalContainerEntityManagerFactoryBean em = new
	 * LocalContainerEntityManagerFactoryBean();
	 * em.setDataSource(primaryDataSource);
	 * em.setPackagesToScan("com.jewelbankers.entity"); // Replace with your entity
	 * package em.setJpaVendorAdapter(new HibernateJpaVendorAdapter()); return em; }
	 * 
	 * @Bean public JpaTransactionManager
	 * transactionManager(LocalContainerEntityManagerFactoryBean
	 * entityManagerFactory) { return new
	 * JpaTransactionManager(entityManagerFactory.getObject()); }
	 */

	
	//  @Bean	  
	// @Qualifier("primary") 
	  //@Primary
	 public DataSource dataSource1() { return
	 DataSourceBuilder.create() .url("jdbc:mysql://localhost:3306/krishnag")
	  .username("root") .password("admin") .build(); }
	 

	//@Bean
	//@Qualifier("secondary")
	public DataSource dataSource2() {
		return DataSourceBuilder.create().url("jdbc:mysql://localhost:3306/roshankag2003").username("root")
				.password("admin").build();
	}
}
