package com.java.hospitalmanagementsystem.repositories;

import com.java.hospitalmanagementsystem.models.CleaningHistory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for CleaningHistory entities. Extends JpaRepository to provide standard CRUD
 * operations.
 */
public interface CleaningHistoryRepository extends JpaRepository<CleaningHistory, Integer> {}
