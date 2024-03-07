package com.java.hotelmanagementsystem.repositories;


import com.java.hotelmanagementsystem.models.TokenType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for TokenType entities. Extends JpaRepository to manage database operations
 * for token types. Includes a method to find a TokenType by its specific enumerated type.
 */
@Repository
public interface TokenTypeRepository extends JpaRepository<TokenType, Integer> {
  Optional<TokenType> findByType(TokenType.TokenTypeEnum type);
}
