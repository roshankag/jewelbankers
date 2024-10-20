package com.jewelbankers.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import io.swagger.v3.oas.annotations.OpenAPIDefinition;
//import io.swagger.v3.oas.annotations.info.Info;

@RestController
@SpringBootApplication
//@EnableJpaRepositories
@EnableJpaRepositories(basePackages = {"com.jewelbankers.repository"}) 
@EntityScan(basePackages = {"com.jewelbankers.entity"})
@ComponentScan({ "com" })
//@OpenAPIDefinition(info = @Info(title = "Jewel Bankers Api",version = "V.1.0",description = "Documentation for Jewel Bankers APIs"))

public class JewelBankers {	
	
	/**
	 * @return	
	 */
	@RequestMapping("/hell") 
	String home() {
		return "Hello World!";	
	}

	@RequestMapping("/roshan1")
	String roshan() {
		return "Hello Roshan!";
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(JewelBankers.class, args);
		//System.out.print();
	}	
}