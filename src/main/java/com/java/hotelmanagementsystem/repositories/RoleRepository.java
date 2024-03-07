package com.java.hotelmanagementsystem.repositories;


import com.java.hotelmanagementsystem.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for Role entities. Extends JpaRepository to facilitate database operations
 * for user roles. Includes a method to find a Role by its name.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
  Optional<Role> findByName(Role.RoleEnum name);
}
