package com.example.backend.security.service;

import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional // wykoannie operacji w jednej trnaskacji
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // wyszkuanie po emailu użytkownika
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));
        // Zerócenie userDetails na podstaiwe user
        return UserDetailsImpl.build(user);
    }
}
