package com.jewelbankers.interceptors;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jewelbankers.configuration.DataSourceService;

@Aspect
@Component
public class DataSourceInterceptor {

    @Autowired
    private DataSourceService dataSourceService;

    // Define a pointcut that matches all service layer methods
    @Before("execution(* com.jewelbankers.services..*(..))")
    public void switchDataSource() throws Throwable {
        // Switch the data source for every API call
        dataSourceService.switchDataSource("ambikam");
        System.out.println("Data source switched to 'ambikam'.");
    }
}
