package com.jewelbankers.controller;

import java.time.Duration;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jewelbankers.entity.ERole;
import com.jewelbankers.entity.Role;
import com.jewelbankers.entity.User;
import com.jewelbankers.jwt.JwtUtils;
import com.jewelbankers.repository.RoleRepository;
import com.jewelbankers.repository.UserRepository;
import com.jewelbankers.request.LoginRequest;
import com.jewelbankers.request.SignupRequest;
import com.jewelbankers.response.JwtResponse;
import com.jewelbankers.response.MessageResponse;
import com.jewelbankers.services.UserDetailsImpl;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/jewelbankersapi/api/auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
	System.out.println(loginRequest.getUsername()+
	  ":"+loginRequest.getPassword());
	  
	 User user = userRepository.findByUsername(loginRequest.getUsername());
	    
	    // Check if user is null (no user found with the provided username)
	    if (user == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
	    }
	    
  
	    LocalDate currentDate = LocalDate.now();
	    
	   
	 // Check subscription and trial status
	    if ("Not Paid".equals(user.getStatus())) {
	        if (user.getStartDate() == null || user.getEndDate() == null) {
	            // Start free trial
	            user.setStartDate(currentDate);
	            user.setEndDate(currentDate.plusWeeks(2));
	            userRepository.save(user);
	        } else if (currentDate.isAfter(user.getEndDate())) {
	            // Trial has ended, transition to Inactive
	            user.setStatus("Inactive");
	            userRepository.save(user);
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
	                "Your free trial has ended. Please complete the registration fee of ₹5000 to continue.");
	        }
	    } else if ("Paid".equals(user.getStatus())) {
	        LocalDate endDate = user.getEndDate();
	        String reminderMessage = null;

	        if (endDate != null) {
	            if (currentDate.isEqual(endDate.minusDays(7))) {
	                reminderMessage = "Your subscription is ending in 7 days. Please renew it to continue.";
	            } else if (currentDate.isEqual(endDate.minusDays(1))) {
	                reminderMessage = "Your subscription is ending tomorrow. Please renew it to avoid interruption.";
	            }
	        }

	        // Check if subscription has expired
	        if (currentDate.isAfter(endDate)) {
	            user.setStatus("Inactive");
	            userRepository.save(user);
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
	                "Your yearly subscription has ended. Please renew it by paying ₹1500 to continue.");
	        }

	        // Include reminder in response if applicable
	        if (reminderMessage != null) {
	            return ResponseEntity.ok(reminderMessage);
	        }
	    } else if ("Inactive".equals(user.getStatus())) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
	            "Your subscription is inactive. Please make the payment to reactivate your subscription.");
	    }
  
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);
    
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();    
    List<String> roles = userDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());

    return ResponseEntity.ok(new JwtResponse(jwt, 
                         userDetails.getId(), 
                         userDetails.getUsername(), 
                         userDetails.getEmail(), 
                         roles));
  }

  @PostMapping("/signup")
  @PreAuthorize("hasRole('ADMIN')")
  @CacheEvict(value = "usersListCache", allEntries = true)
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
	
	// Check if username or email already exists
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: Username is already taken!"));
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: Email is already in use!"));
    }
    
 // Check if userDatabaseName is already in use
    if (userRepository.existsByUserDatabaseName(signUpRequest.getUserDatabaseName())) {
        return ResponseEntity
            .badRequest()
            .body(new MessageResponse("Error: Database name is already in use!"));
    }

 // Create new user's account
    User user = new User(
        signUpRequest.getUsername(), 
        signUpRequest.getEmail(),
        encoder.encode(signUpRequest.getPassword()),
        signUpRequest.getUserDatabaseName() // Include userDatabaseName here
    );
    
    // Set roles for the user
    Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();

    if (strRoles == null) {
      Role userRole = roleRepository.findByName(ERole.ROLE_USER)
          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
        case "admin":
          Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(adminRole);

          break;
        case "mod":
          Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(modRole);

          break;
        default:
          Role userRole = roleRepository.findByName(ERole.ROLE_USER)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(userRole);
        }
      });
    }

    user.setRoles(roles);
    
 // Handle subscription logic
    LocalDate currentDate = LocalDate.now();
    String message;

    if ("Paid".equals(signUpRequest.getStatus())) {
        // If the user pays, assign yearly subscription
        user.setStatus("Paid");
        user.setStartDate(currentDate);
        user.setEndDate(currentDate.plusYears(1)); // Start yearly subscription immediately
        message = "User registered successfully! Yearly subscription activated. Reminders will be sent.";

        // Schedule reminders
        scheduleReminder(user.getEmail(), currentDate.plusYears(1).minusDays(7), 
            "Reminder: Your subscription is ending in 7 days. Please renew.");
        
        scheduleReminder(user.getEmail(), currentDate.plusYears(1).minusDays(1), 
            "Reminder: Your subscription is ending tomorrow. Please renew.");
        
    } else if ("Not Paid".equals(signUpRequest.getStatus())) {
        // If the user doesn't pay, start a free trial for 2 weeks
        user.setStatus("Not Paid");
        user.setStartDate(currentDate);
        user.setEndDate(currentDate.plusWeeks(2)); // 2-week free trial
        message = "User registered successfully! "
        		+ "Free trial for 2 weeks started. Please make the payment to activate your yearly subscription.";

        // Schedule reminder at the end of the trial
        scheduleReminder(user.getEmail(), currentDate.plusWeeks(2), 
            "Your free trial has ended. Please complete the payment to activate your yearly subscription.");
        
    } else if ("Inactive".equals(signUpRequest.getStatus())) {
        // Handle inactive users who have not paid
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
            new MessageResponse("Your account is inactive. "
            		+ "Please complete the payment to reactivate your subscription."));
        
    } else {
        // Handle unexpected statuses
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            new MessageResponse("Invalid status provided. Please check and try again."));
        
    }
    userRepository.save(user);

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }
  
  private void scheduleReminder(String email, LocalDate reminderDate, String message) {
	    // Check if the reminder date is in the future
	    if (reminderDate.isAfter(LocalDate.now())) {
	        long delay = Duration.between(LocalDate.now().atStartOfDay(), reminderDate.atStartOfDay()).toMillis();

	        // Use a single-threaded executor to schedule tasks
	        Executors.newSingleThreadScheduledExecutor().schedule(() -> {
	            sendMessage(email, message);
	        }, delay, TimeUnit.MILLISECONDS);
	    }
	}

	private void sendMessage(String email, String message) {
	    // Logic to send email or notification
	    System.out.println("Sending to " + email + ": " + message);
	}
  
}