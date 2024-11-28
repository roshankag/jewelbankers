package com.jewelbankers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jewelbankers.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  public User findByUsername(String username);
  
  public User findByUserDatabaseName(String userDatabaseName);

  Boolean existsByUsername(String username);

  public Boolean existsByEmail(String email);
  
  public User findByResetPasswordToken(String token);
  
//  User findByname(String username);

  public User findByEmail(String email);
}
