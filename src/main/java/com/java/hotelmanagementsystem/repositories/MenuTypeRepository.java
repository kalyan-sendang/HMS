package com.java.hotelmanagementsystem.repositories;

import com.java.hotelmanagementsystem.models.MenuType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for MenuType entities. Extends JpaRepository to facilitate database
 * operations for menu types. Includes a method to find a MenuType by its name.
 */
@Repository
public interface MenuTypeRepository extends JpaRepository<MenuType, Integer> {
  MenuType findByName(MenuType.MenuEnum menuTypeName);
}
