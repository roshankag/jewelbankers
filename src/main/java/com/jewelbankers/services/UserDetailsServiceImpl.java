package com.jewelbankers.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jewelbankers.entity.User;
import com.jewelbankers.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
	
  @Autowired
  UserRepository userRepository;

//  @Autowired
//  PasswordEncoder encoder;
  
  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      System.out.println("Username: " + username);
      User user = userRepository.findByUsername(username);
      if (user == null) {
          throw new UsernameNotFoundException("User Not Found with username: " + username);
      }
      return UserDetailsImpl.build(user);
  }
  	
// 
  
  @Transactional
  public UserDetails loadUserByDataBase(String userDatabaseName) throws UsernameNotFoundException {
      log.debug("Attempting to find user with database name: {}", userDatabaseName);
      User user = userRepository.findByUserDatabaseName(userDatabaseName);
      if (user == null) {
          log.error("User not found with database name: {}", userDatabaseName);
          throw new UsernameNotFoundException(
              "UserDetailsServiceImpl loadUserByDataBase -> User Not Found with DataBaseName: " + userDatabaseName);
      }
      log.debug("User found: {}", user);
      return UserDetailsImpl.build(user);
  }

  
  

  @Transactional
  public UserDetailsImpl loadUserByEmail(String username) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(username);
    //user.getUserDatabaseName();
    System.out.println("DataBase:"+ user.getUserDatabaseName());

    return UserDetailsImpl.build(user);
  }

  @Transactional
  public void deleteUserById(Long id) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with id: " + id));
    userRepository.delete(user);
  }


public void updateResetPasswordToken(String token, String email) throws UsernameNotFoundException {
      User userDetails =  userRepository.findByEmail(email); 
        if (userDetails != null) {
            userDetails.setResetPasswordToken(token);
            userRepository.save(userDetails);
        } else {
            throw new UsernameNotFoundException("Could not find any user with the email " + email);
        }
    }
     
    public User getByResetPasswordToken(String token) {
        return userRepository.findByResetPasswordToken(token);
    }
     
   
    public void updatePassword(User user, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
         
        user.setResetPasswordToken(null);
        userRepository.save(user);
    }


    public List<User> allUsers() {
        List<User> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);
        
        for (User user : users) {
            // Set unwanted fields to null
            user.setPassword(null);  // Assuming you don't want to expose the password
            user.setResetPasswordToken(null);  // Set other unwanted fields to null
            // Keep the fields you need exposed, like id, username, email, etc.
        }

        return users;
    }
    
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    
    public User updateUser(Long id, User user) {
        System.out.println("Updating user with ID: " + id);
        try {
            User existingUser = userRepository.findById(id).orElse(null);
            if (existingUser != null) {
                // Update user details
                existingUser.setUsername(user.getUsername());                
                //existingUser.setPassword(encoder.encode(user.getPassword()));
                existingUser.setUserDatabaseName(user.getUserDatabaseName());

                LocalDate currentDate = LocalDate.now();

                // Handle transitions from "Not Paid" to "Paid"
                if (!existingUser.isStatus() && user.isStatus()) {
                    // Update payment status and subscription dates
                    existingUser.setStatus(true);
                    existingUser.setStartDate(currentDate);
                    existingUser.setEndDate(currentDate.plusYears(1)); // Start yearly subscription

                    // Send confirmation message
                    sendMessage(existingUser.getEmail(),
                        "Thank you for your payment! Your yearly subscription has started and will end on "
                        + currentDate.plusYears(1) + ".");
                }
                
             // Handle transitions from "Paid" to "Not Paid"
                else if (existingUser.isStatus() && !user.isStatus()) {
                    // Update payment status and start a free trial
                    existingUser.setStatus(false);
                    existingUser.setStartDate(currentDate);
                    existingUser.setEndDate(currentDate.plusWeeks(2)); // Start 2-week free trial

                    // Send notification about the free trial
                    sendMessage(existingUser.getEmail(),
                        "Your subscription has been updated to 'Not Paid'. Your free trial has started and will end on "
                        + currentDate.plusWeeks(2) + ". Please make the payment to continue after the trial period.");
                }
                
                // Handle end of free trial
                if (!existingUser.isStatus() && currentDate.isEqual(existingUser.getEndDate())) {
                    // Free trial has ended
                    sendMessage(existingUser.getEmail(),
                        "Your free trial has ended. Please make the payment to continue using the service.");
                    existingUser.setStatus(false); // User remains unpaid
                }

                // Save and return updated user
                return userRepository.save(existingUser);
            } else {
                System.out.println("User with ID " + id + " not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    public User getUserByUsername(String username) {
//        return userRepository.findByname(username);
//    }
    
    private void sendMessage(String email, String message) {
        // Logic to send email or notification
        System.out.println("Sending to " + email + ": " + message);
    }
    

}