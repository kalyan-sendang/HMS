package com.java.hospitalmanagementsystem.repositories;


import com.java.hospitalmanagementsystem.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for Role entities. Extends JpaRepository to facilitate database operations
 * for user roles. Includes a method to find a Role by its name.
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {
  Optional<Role> findByName(Role.RoleEnum name);
}
