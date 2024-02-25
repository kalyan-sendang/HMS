package com.java.hospitalmanagementsystem.repositories;

import com.java.hospitalmanagementsystem.models.EntertainmentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for EntertainmentType entities. Extends JpaRepository to provide standard
 * database operations. Includes a method to find an EntertainmentType by its name.
 */
public interface EntertainmentTypeRepository extends JpaRepository<EntertainmentType, Integer> {
  Optional<EntertainmentType> findByName(String paymentType);
}
