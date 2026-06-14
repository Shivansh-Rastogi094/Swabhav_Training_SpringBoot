package com.monocept.Repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.monocept.Model.Users;

public interface UserRepo extends JpaRepository<Users, Long> {
	Optional<Users> findByUsername(String username);

    boolean existsByUsername(String username);
	
}
