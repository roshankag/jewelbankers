package com.jewelbankers.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.social.ResourceNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jewelbankers.aop.SwitchUserDatabase;
import com.jewelbankers.entity.User;
import com.jewelbankers.services.UserDetailsServiceImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@SwitchUserDatabase
@RequestMapping("/jewelbankersapi/api/users")
public class UserController {
	private final UserDetailsServiceImpl userService;

	public UserController(UserDetailsServiceImpl userService) {
		this.userService = userService;
	}

	@GetMapping("/list")
	@PreAuthorize("hasRole('ADMIN')")
	// @Cacheable(value = "usersListCache")
	public ResponseEntity<List<User>> adminAccess() {
		List<User> users = userService.allUsers();
		return ResponseEntity.ok(users);
	}

	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	//@CacheEvict(value = "usersListCache", allEntries = true)
	public ResponseEntity<?> deleteUser(@PathVariable Long id) {
		try {
			userService.deleteUserById(id);
			return ResponseEntity.ok().build();
		} catch (ResourceNotFoundException ex) {
			ex.printStackTrace();
			return ResponseEntity.notFound().build();
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.status(500).body("An error occurred while deleting the user.");
		}
	}

	@PutMapping("/edit/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	//@CacheEvict(value = "usersListCache", allEntries = true)
	public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
		User updatedUser = userService.updateUser(id, user);
		if (updatedUser != null) {
			return ResponseEntity.ok(updatedUser);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

//   @GetMapping("/username/{username}")
//   public ResponseEntity<User> getUserByUsername(@PathVariable("username") String username) {
//       try {
//           User user = userService.getUserByUsername(username);
//           if (user != null) {
//               return ResponseEntity.ok(user);
//           } else {
//               throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
//           }
//       } catch (Exception e) {
//           throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
//       }
//   }
}