package com.monocept.Config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.monocept.Model.Role;
import com.monocept.Model.Users;
import com.monocept.Repo.RoleRepo;
import com.monocept.Repo.UserRepo;


@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initUsersAndRoles(UserRepo userRepository, RoleRepo roleRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            Role adminRole = roleRepository.findByName("ROLE_ADMIN").orElseGet(() -> {
                Role role = new Role();
                role.setName("ROLE_ADMIN");
                return roleRepository.save(role);
            });

            Role userRole = roleRepository.findByName("ROLE_USER").orElseGet(() -> {
                Role role = new Role();
                role.setName("ROLE_USER");
                return roleRepository.save(role);
            });

            if (!userRepository.existsByUsername("admin")) {
                Users admin = new Users();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setEnabled(true);
                admin.setRoles(new HashSet<>(Set.of(adminRole, userRole)));
                userRepository.save(admin);
            }

            if (!userRepository.existsByUsername("user")) {
            	Users user = new Users();
                user.setUsername("user");
                user.setPassword(passwordEncoder.encode("user123"));
                user.setEnabled(true);
                user.setRoles(new HashSet<>(Set.of(userRole)));
                userRepository.save(user);
            }
        };
    }
}
