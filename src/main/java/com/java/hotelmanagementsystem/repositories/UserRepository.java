package com.java.hotelmanagementsystem.repositories;

import com.java.hotelmanagementsystem.models.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    Optional<User> findByPhone(String phone);

    List<User> findByFirstNameContainingAndLastNameContaining(
            String firstName, String lastName, Pageable pageable);

    List<User> findByFirstNameContainingOrLastNameContaining(
            String firstName, String lastName, Pageable pageable);
}
