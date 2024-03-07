package com.java.hotelmanagementsystem.repositories;

import com.java.hotelmanagementsystem.models.EntertainmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for EntertainmentType entities. Extends JpaRepository to provide standard
 * database operations. Includes a method to find an EntertainmentType by its name.
 */
@Repository
public interface EntertainmentTypeRepository extends JpaRepository<EntertainmentType, Integer> {
  Optional<EntertainmentType> findByName(String paymentType);
}
