package com.java.hotelmanagementsystem.repositories;

import com.java.hotelmanagementsystem.models.CleaningHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for CleaningHistory entities. Extends JpaRepository to provide standard CRUD
 * operations.
 */
@Repository
public interface CleaningHistoryRepository extends JpaRepository<CleaningHistory, Integer> {}
