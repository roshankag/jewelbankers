package com.jewelbankers.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jewelbankers.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);

  Boolean existsByUsername(String username);

  public Boolean existsByEmail(String email);
  
  public User findByResetPasswordToken(String token);

  public User findByEmail(String email);
}
