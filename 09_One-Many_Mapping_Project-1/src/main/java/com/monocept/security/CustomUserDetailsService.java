package com.monocept.security;

import java.util.List;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.monocept.Model.Users;
import com.monocept.Repo.UserRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Loading user from database with username: {}", username);
        Users user = userRepo.findByUsername(username).orElseThrow(() -> {
            log.warn("User not found with username: {}", username);
            return new UsernameNotFoundException("User not found with username: " + username);
        });
        List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .toList();
        return new User(user.getUsername(), user.getPassword(), user.isEnabled(), true, true, true, authorities);
    }
}
