package com.jewelbankers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

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
        try {
            users user = userService.getUserById(id);
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }
    }

    @PostMapping
    public ResponseEntity<users> createUser(@RequestBody users user) {
        try {
            users createdUser = userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<users> updateUser(@PathVariable("id") Long id, @RequestBody users userDetails) {
        try {
            users updatedUser = userService.updateUser(id, userDetails);
            if (updatedUser != null) {
                return ResponseEntity.ok(updatedUser);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) {
        try {
            String message = userService.deleteUser(id);
            if (message != null && message.startsWith("User with ID " + id + " deleted")) {
                return ResponseEntity.ok(message);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<users> getUserByUsername(@PathVariable("username") String username) {
        try {
            users user = userService.getUserByUsername(username);
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }
    }
}
