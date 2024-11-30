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

    // Pointcut for methods in classes annotated with @SwitchDatabase
    @Pointcut("@within(com.jewelbankers.aop.SwitchDatabase)") 
    public void switchDatabase() {
        // This pointcut matches methods within classes annotated with @SwitchDatabase
    }

    // Pointcut for methods in classes or methods annotated with @SwitchUserDatabase
    @Pointcut("@within(com.jewelbankers.aop.SwitchUserDatabase) || @annotation(com.jewelbankers.aop.SwitchUserDatabase)")
    public void switchUserDatabasePointcut() {
        // This pointcut matches methods in classes or methods annotated with @SwitchUserDatabase
    }

    // Advice for switching the database based on @SwitchUserDatabase annotation
    @Before("switchUserDatabasePointcut()")
    public void switchToUserDatabase() {
        System.out.println("Switching to default user database: " + DataSourceConfig.JEWEL_BANKERS);
        dataSourceService.switchDataSource(DataSourceConfig.JEWEL_BANKERS);
    }

    // Advice for switching the database based on JWT token
    @Before("switchDatabase()")
    public void switchToDatabaseFromJwt() {
        String authHeader = httpServletRequest.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            if (jwtUtils.validateJwtToken(token)) {
                String databaseName = jwtUtils.getRequestUserdatabaseFromJwtToken(token);
                System.out.println("Switching to database: " + databaseName);
                dataSourceService.switchDataSource(databaseName); // Dynamically switch the database
            } else {
                handleInvalidToken();
            }
        } else {
            handleInvalidToken();
        }
    }

    // Helper method to handle invalid or missing token scenario
    private void handleInvalidToken() {
        System.out.println("Invalid or missing JWT token, switching to default database.");
        dataSourceService.switchDataSource(DataSourceConfig.JEWEL_BANKERS);
        throw new IllegalArgumentException("Invalid JWT Token or missing Authorization header");
    }
}
