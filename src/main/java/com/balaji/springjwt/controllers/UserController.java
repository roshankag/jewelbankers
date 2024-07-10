package com.balaji.springjwt.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.ResourceNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.balaji.springjwt.models.User;
import com.balaji.springjwt.repository.UserRepository;
import com.balaji.springjwt.security.services.UserDetailsServiceImpl;
import com.balaji.springjwt.services.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
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
}