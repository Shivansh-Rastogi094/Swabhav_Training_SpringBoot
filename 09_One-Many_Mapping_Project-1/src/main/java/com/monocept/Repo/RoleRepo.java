package com.monocept.Repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.monocept.Model.Role;


public interface RoleRepo extends JpaRepository<Role, Long>{
	Optional<Role> findByName(String name);
}
