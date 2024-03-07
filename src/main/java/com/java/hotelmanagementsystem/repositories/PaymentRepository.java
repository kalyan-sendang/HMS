package com.java.hotelmanagementsystem.repositories;

import com.java.hotelmanagementsystem.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Payment entities. Extends JpaRepository to provide standard CRUD
 * operations for handling payments.
 */
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {}
