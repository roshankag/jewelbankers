package com.jewelbankers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jewelbankers.entity.users;

@Repository
public interface UserRepositoryAPI extends JpaRepository<users, Long> {
    
    users findByUsername(String username);
}

