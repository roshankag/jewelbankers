package com.jewelbankers.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;

//@Configuration
//@EnableJpaRepositories(basePackages = "com.balaji.springjwt.repository")
public class JpaConfig {

	//@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("primary") DataSource dataSource) {
		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		emf.setDataSource(dataSource);
		emf.setPackagesToScan("com.balaji.springjwt.models"); // Adjust package as needed
		emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		return emf;
	}

	//@Bean
	public JpaTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactory) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory.getObject());
		return transactionManager;
	}
	
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
