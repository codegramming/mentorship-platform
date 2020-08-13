package com.tayfurunal.mentorship.security;

import com.tayfurunal.mentorship.domain.User;
import com.tayfurunal.mentorship.exception.ResourceNotFoundException;
import com.tayfurunal.mentorship.repository.jpa.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        if (user.getUsername().equals("admin")) {
            user.setAuthorities(Collections.singleton(Role.ADMIN));
            System.out.println("ADMIN CREATED");
        } else {
            user.setAuthorities(Collections.singleton(Role.USER));
        }
        return user;
    }

    @Transactional(readOnly = true)
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        if (user.getUsername().equals("admin")) {
            user.setAuthorities(Collections.singleton(Role.ADMIN));
            System.out.println("ADMIN CREATED");
        } else {
            user.setAuthorities(Collections.singleton(Role.USER));
        }
        return user;
    }
}