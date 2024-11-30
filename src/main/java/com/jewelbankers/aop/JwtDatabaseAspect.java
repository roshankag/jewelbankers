package com.jewelbankers.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.jewelbankers.configuration.DataSourceConfig;
import com.jewelbankers.configuration.DataSourceService;
import com.jewelbankers.jwt.JwtUtils;

import jakarta.servlet.http.HttpServletRequest;

@Aspect
@Component
@Order(1)
public class JwtDatabaseAspect {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private DataSourceService dataSourceService;

    //@Before("@annotation(com.jewelbankers.aop.SwitchDatabase)")
    
    @Pointcut("@within(com.jewelbankers.aop.SwitchDatabase)") 
    public void switchDatabaseClassMethods() {
        // This pointcut matches all methods within classes annotated with @SwitchDatabase
    }

    @Pointcut("@within(com.jewelbankers.aop.SwitchUserDatabase) || @annotation(com.jewelbankers.aop.SwitchUserDatabase)")
    public void switchUserDatabasePointcut() {
        // This pointcut matches all methods in classes annotated with @SwitchUserDatabase
        // and also methods directly annotated with @SwitchUserDatabase
    }

    @Before("switchUserDatabasePointcut()")
    public void switchUserDatabase() {
        // This advice is triggered when methods in classes annotated with @SwitchUserDatabase
        // or methods directly annotated with @SwitchUserDatabase are executed.
        System.out.println("JwtDatabaseAspect.switchUserDatabase -> Switching to DataBase Name: " + DataSourceConfig.JEWEL_BANKERS);
        dataSourceService.switchDataSource(DataSourceConfig.JEWEL_BANKERS);
    }
    
    // Before advice that runs when the pointcut is matched
    @Before("switchDatabaseClassMethods()")    
    //@Pointcut("@within(com.jewelbankers.aop.SwitchDatabase)") 
    public void switchDatabase() {
    	
        // Get Authorization header
        String authHeader = httpServletRequest.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            // Validate the token and extract the database name
            if (jwtUtils.validateJwtToken(token)) {
                String databaseName = jwtUtils.getRequestUserdatabaseFromJwtToken(token);

                // Log or switch the data source
                //System.out.println("Switching to Database: " + databaseName);

                System.out.println("JwtDatabaseAspect.switchDatabase -> Switching to Database:: " + databaseName);
dataSourceService.switchDataSource(databaseName); // Dynamically switch the database
            } else {
                dataSourceService.switchDataSource(DataSourceConfig.JEWEL_BANKERS); // Dynamically switch the database
                throw new IllegalArgumentException("Invalid JWT Token");
            }
        } else {
            dataSourceService.switchDataSource(DataSourceConfig.JEWEL_BANKERS); // Dynamically switch the database
            //throw new IllegalArgumentException("Authorization header missing or invalid");
        }
    }
}