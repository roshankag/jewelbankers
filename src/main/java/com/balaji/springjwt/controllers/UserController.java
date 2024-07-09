package com.balaji.springjwt.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.balaji.springjwt.models.User;
import com.balaji.springjwt.security.services.UserDetailsServiceImpl;

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
}