package com.java.hospitalmanagementsystem.repositories;

import com.java.hospitalmanagementsystem.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for Payment entities. Extends JpaRepository to provide standard CRUD
 * operations for handling payments.
 */
public interface PaymentRepository extends JpaRepository<Payment, Integer> {}
