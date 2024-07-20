package com.jewelbankers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jewelbankers.entity.users;
import com.jewelbankers.services.UserServices;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserControllerAPI {

    @Autowired
    private UserServices userService;

    @GetMapping
    public List<users> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<users> getUserById(@PathVariable("id") Long id) {
        users user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public users createUser(@RequestBody users user) {
        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<users> updateUser(@PathVariable("id") Long id, @RequestBody users userDetails) {
        users updatedUser = userService.updateUser(id, userDetails);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) {
        String message = userService.deleteUser(id);
        if (message != null && message.startsWith("User with ID " + id + " deleted")) {
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<users> getUserByUsername(@PathVariable("username") String username) {
        users user = userService.getUserByUsername(username);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
