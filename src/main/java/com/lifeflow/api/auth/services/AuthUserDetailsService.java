package com.lifeflow.api.auth.services;

import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.lifeflow.api.user.entities.UserEntity;
import com.lifeflow.api.user.repositories.UserRepository;

@Service
public class AuthUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public AuthUserDetailsService(UserRepository repo) {
        this.userRepository = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return org.springframework.security.core.userdetails.User
            .withUsername(user.getEmail())
            .password(user.getPassword())
            .build();
    }
}
