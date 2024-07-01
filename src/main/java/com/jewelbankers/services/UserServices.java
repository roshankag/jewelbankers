package com.jewelbankers.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jewelbankers.entity.users;
import com.jewelbankers.repository.UserRepository;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServices {

    @Autowired
    private UserRepository userRepository;

    public List<users> getAllUsers() {
        return userRepository.findAll();
    }

    public users getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Transactional
    public users createUser(users user) {
        // Validate username
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        // Check if username already exists
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new IllegalArgumentException("Username already exists");
        }
        // Validate password
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        return userRepository.save(user);
    }

    public users updateUser(Long id, users userDetails) {
        users user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setUsername(userDetails.getUsername());
            user.setPassword(userDetails.getPassword());
            return userRepository.save(user);
        }
        return null;
    }

    @Transactional
    public String deleteUser(Long id) {
        Optional<users> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            userRepository.deleteById(id);
            return "User with ID " + id + " deleted successfully";
        } else {
            return "User with ID " + id + " does not exist";
        }
    }

    public users getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}

