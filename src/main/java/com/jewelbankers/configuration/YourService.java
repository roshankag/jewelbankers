package com.jewelbankers.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class YourService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String performDatabaseOperation() {
        // Your database operations here
        return jdbcTemplate.queryForObject("SELECT DATABASE()", String.class);
    }
}
