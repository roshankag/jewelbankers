package com.jewelbankers.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.jewelbankers.repository"}) 
@EntityScan(basePackages = {"com.jewelbankers.entity"})
//@EnableCaching
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan({ "com" })
//@OpenAPIDefinition(info = @Info(title = "Jewel Bankers Api",version = "V.1.0",description = "Documentation for Jewel Bankers APIs"))

public class JewelBankers {	

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
		
		/*
		 * BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); String
		 * encodedPassword = passwordEncoder.encode("pass123");
		 * System.out.println("Updated password:"+encodedPassword);
		 */
	         
//	         // Encode the password
//	         String rawPassword = "Roshan@2003";
//	         String encodedPassword1 = passwordEncoder.encode(rawPassword);
//	         System.out.println("Encoded password: " + encodedPassword1);
//	         
//	         // Now, verify the password
//	         boolean isMatch = passwordEncoder.matches(rawPassword, encodedPassword1);
//	         System.out.println("Does the raw password match the encoded password? " + isMatch);
	         
	        
	         
	
		 
	}	
}