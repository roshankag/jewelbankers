package com.jewelbankers.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DatabaseSwitchController {

    @Autowired
    private YourService yourService;
	/*
	 * @GetMapping("/switchToDatabase1") public String switchToDatabase1() {
	 * DynamicDataSource.setDataSourceKey("dataSource1"); // Now the operations will
	 * use dataSource1 return yourService.performDatabaseOperation(); }
	 * 
	 * @GetMapping("/switchToDatabase2") public String switchToDatabase2() {
	 * DynamicDataSource.setDataSourceKey("dataSource2"); // Now the operations will
	 * use dataSource2 return yourService.performDatabaseOperation(); }
	 */
}

