package com.java.hotelmanagementsystem.repositories;

import com.java.hotelmanagementsystem.models.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for MenuItem entities. Extends JpaRepository to provide standard CRUD
 * operations for menu items.
 */
@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Integer> {}
