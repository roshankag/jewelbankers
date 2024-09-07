package com.jewelbankers.springjwt.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jewelbankers.entity.User;
import com.jewelbankers.entity.UserPrincipal;
import com.jewelbankers.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        UserPrincipal userPrincaple = new UserPrincipal(user);
        return userPrincaple;
    }

    

    public boolean ifEmailExist(String mail){
        return userRepository.existsByEmail(mail);
    }

    public User getUserByMail(String mail){
        return userRepository.findByEmail(mail);
    }

    public User saveUser(User user){
        return userRepository.save(user);
    }
}
