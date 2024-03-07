package com.java.hotelmanagementsystem.repositories;

import com.java.hotelmanagementsystem.models.RoomReservation;
import com.java.hotelmanagementsystem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

/**
 * Repository interface for RoomReservation entities. Extends JpaRepository to manage database
 * operations for room reservations. Includes methods to find reservations by user, those without
 * payments, and reservations within a specific date range.
 */
@Repository
public interface RoomReservationRepository extends JpaRepository<RoomReservation, Integer> {
  RoomReservation findFirstByUserOrderByBookedAtDesc(User user);

  List<RoomReservation> findAllByUser(User user);

  List<RoomReservation> findAllByPaymentIsNull();

  List<RoomReservation> findAllByFromDateBetweenOrToDateBetween(
      Date date, Date date1, Date date2, Date date3);
}
