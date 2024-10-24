package com.jewelbankers.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.jewelbankers.jwt.AuthEntryPointJwt;
import com.jewelbankers.jwt.AuthTokenFilter;
import com.jewelbankers.services.UserDetailsServiceImpl;

@Configuration
@EnableMethodSecurity
 (securedEnabled = true,
 jsr250Enabled = true,
 prePostEnabled = true) // by default
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig  {//extends WebSecurityConfigurerAdapter {
  @Autowired
  UserDetailsServiceImpl userDetailsService;

  @Autowired
  private AuthEntryPointJwt unauthorizedHandler;

  @Bean
  public AuthTokenFilter authenticationJwtTokenFilter() {
    return new AuthTokenFilter();
  }

  @Bean
  public WebMvcConfigurer corsConfigurer() {
      return new WebMvcConfigurer() {
          @Override
          public void addCorsMappings(CorsRegistry registry) {
              registry.addMapping("/**").allowedOrigins("http://localhost:4200","http://localhost:8080")
              //.allowedOrigins("http://localhost:4200","http://localhost","http://ec2-54-204-78-129.compute-1.amazonaws.com","http://localhost")
             .allowedOrigins("*")
              .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                      .allowedHeaders("*");
                      //.allowCredentials(true);
          }
      };
  }
  
  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
       
      authProvider.setUserDetailsService(userDetailsService);
      authProvider.setPasswordEncoder(passwordEncoder());
   
      return authProvider;
  }

//  @Bean
//  @Override
//  public AuthenticationManager authenticationManagerBean() throws Exception {
//    return super.authenticationManagerBean();
//  }
  
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
    return authConfig.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
  
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.cors().and().csrf().disable()
        .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> 
//          auth.requestMatchers("/api/auth/**").permitAll().requestMatchers("/social/**").permitAll()
//              .requestMatchers("/api/test/**").permitAll()
//              .requestMatchers("/api/pledge/**").permitAll()
//              .requestMatchers("/api/users/**").permitAll()
//              .requestMatchers("/api/customers/**").permitAll()
//              .requestMatchers("/export/excel/**").permitAll()
//              .requestMatchers(AUTH_WHITELIST).permitAll()
//              .requestMatchers("/forgot-password/**").permitAll()
//              .requestMatchers("/jewelbankersapi/**").permitAll()
//              .requestMatchers("/", "/index.html", "/static/**", "/browser/**").permitAll()  // Allow static resources
      
        
        auth.requestMatchers("/", "/index.html", "/**", "/static/**", "/browser/**","/jewelbankersapi/**",
        		"/jewelbankersapi/social/**","/jewelbankersapi/api/**","/jewelbankersapi/forgot-password/" // Allow access to all api's
//        		,"/**/*.js", // Allow access to all JavaScript files
//                "/**/*.css", // Allow access to all CSS files
//                "/**/*.png", // Allow access to PNG files (or other image formats as needed)
//                "/**/*.jpg", // Allow access to JPG files
//                "/**/*.jpeg", // Allow access to JPEG files
//                "/**/*.gif", // Allow access to GIF files
//                "/**/*.ico" // Allow access to favicon files
        		).permitAll() // Allow access to browser resources   

//        auth.anyRequest().permitAll()
              .anyRequest().authenticated()
        );
    
    http.authenticationProvider(authenticationProvider());

    http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    
    return http.build();
  }
  
}