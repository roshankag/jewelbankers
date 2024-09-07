package com.jewelbankers.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.social.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jewelbankers.entity.User;
import com.jewelbankers.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  UserRepository userRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

    return UserDetailsImpl.build(user);
  }

  @Transactional
  public UserDetails loadUserByEmail(String username) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(username);

    return UserDetailsImpl.build(user);
  }

  @Transactional
  public void deleteUserById(Long id) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with id: " + id));
    userRepository.delete(user);
  }
  
//  public User updateUser(Long id, User user) {
//	    User existingUser = userRepository.findById(id)
//	        .orElseThrow(() -> new ResourceNotFoundException("Message", "User not found"));
//	    
//	    existingUser.setEmail(user.getEmail());
//	    existingUser.setUsername(user.getUsername());
//	    existingUser.setRoles(user.getRoles());
//	    
//	    return userRepository.save(existingUser);
//	}
  
  public User updateUser(Long id, User userData) {
      // Find existing user
      User existingUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

      // Update user fields
      existingUser.setEmail(userData.getEmail());
      existingUser.setPassword(userData.getPassword());
      existingUser.setUsername(userData.getUsername());
      existingUser.setRoles(userData.getRoles());

      // Save updated user
      return userRepository.save(existingUser);
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

        return users;
    }
    
    

}
