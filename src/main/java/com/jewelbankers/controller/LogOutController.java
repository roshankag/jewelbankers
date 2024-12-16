	package com.jewelbankers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jewelbankers.configuration.DataSourceConfig;
import com.jewelbankers.configuration.DataSourceService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/jewelbankersapi")
public class LogOutController {
	
	@Autowired
	private DataSourceService dataSourceService;
	
	@GetMapping("/logout")
	//@SwitchDatabase
	public String logout() {
		dataSourceService.switchDataSource(DataSourceConfig.JEWEL_BANKERS);
	    return "Logout success";  
	}

}