package com.swabhav.demo.security;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.swabhav.demo.model.AppUser;
import com.swabhav.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Loading user from database with username: {}", username);
        AppUser appUser = userRepository.findByUsername(username).orElseThrow(() -> {
            log.warn("User not found with username: {}", username);
            return new UsernameNotFoundException("User not found with username: " + username);
        });
        List<SimpleGrantedAuthority> authorities = appUser.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .toList();
        return new User(appUser.getUsername(), appUser.getPassword(), appUser.isEnabled(), true, true, true, authorities);
    }
}
