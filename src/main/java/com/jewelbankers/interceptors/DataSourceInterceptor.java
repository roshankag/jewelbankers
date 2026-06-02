package com.jewelbankers.interceptors;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.jewelbankers.configuration.DataSourceConfig;
import com.jewelbankers.configuration.DataSourceService;
import com.jewelbankers.jwt.JwtUtils;

import jakarta.servlet.http.HttpServletRequest;

@Aspect
@Component
public class DataSourceInterceptor {

    @Autowired
    private DataSourceService dataSourceService;

    @Autowired
    private JwtUtils jwtUtils;

    // Define a pointcut that matches all service layer methods
    @Before("execution(* com.jewelbankers.services..*(..))")
    public void switchDataSource() throws Throwable {
        String token = parseJwt();

        if (token != null && jwtUtils.validateJwtToken(token)) {
            String databaseName = jwtUtils.getRequestUserdatabaseFromJwtToken(token);

            if (dataSourceService.isDataSourceRegistered(databaseName)) {
                dataSourceService.switchDataSource(databaseName);
                System.out.println("Data source switched to '" + databaseName + "'.");
            } else {
                // Database from JWT is not registered — fall back to primary
                System.out.println("WARNING: Datasource '" + databaseName + "' is not registered. Falling back to primary.");
                dataSourceService.switchDataSource(DataSourceConfig.JEWEL_BANKERS);
            }
        } else {
            // No JWT or not an HTTP request (e.g., startup) — use primary datasource
            dataSourceService.switchDataSource(DataSourceConfig.JEWEL_BANKERS);
            System.out.println("No valid JWT token; switched to default datasource '" + DataSourceConfig.JEWEL_BANKERS + "'.");
        }
    }

    private String parseJwt() {
        try {
            ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            String headerAuth = request.getHeader("Authorization");
            if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
                return headerAuth.substring(7);
            }
        } catch (IllegalStateException e) {
            // No active HTTP request (e.g., called during application startup)
            return null;
        }
        return null;
    }
}
