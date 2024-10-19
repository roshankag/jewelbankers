package com.jewelbankers.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.ResourceNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jewelbankers.entity.User;
import com.jewelbankers.repository.UserRepository;
import com.jewelbankers.services.UserDetailsServiceImpl;
import com.jewelbankers.services.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/jewelbankersapi/api/users")
public class UserController {
    private final UserDetailsServiceImpl userService;

    public UserController(UserDetailsServiceImpl userService) {
        this.userService = userService;
    }
  @GetMapping("/list")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<List<User>> adminAccess() {
      List <User> users = userService.allUsers();
    return ResponseEntity.ok(users);
  }


   @DeleteMapping("/delete/{id}")
   @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUserById(id);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("An error occurred while deleting the user.");
        }
    }
   
   @PutMapping("/edit/{id}")
   @PreAuthorize("hasRole('ADMIN')")
   public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
       User updatedUser = userService.updateUser(id, user);
       return ResponseEntity.ok(updatedUser);
   }
}