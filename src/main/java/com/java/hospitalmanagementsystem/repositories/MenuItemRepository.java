package com.java.hospitalmanagementsystem.repositories;

import com.java.hospitalmanagementsystem.models.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for MenuItem entities. Extends JpaRepository to provide standard CRUD
 * operations for menu items.
 */
public interface MenuItemRepository extends JpaRepository<MenuItem, Integer> {}
