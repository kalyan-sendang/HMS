package com.java.hotelmanagementsystem.repositories;

import com.java.hotelmanagementsystem.models.EntertainmentReservation;
import com.java.hotelmanagementsystem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

/**
 * Repository interface for EntertainmentReservation entities. Extends JpaRepository to facilitate
 * database operations for entertainment reservations. Includes methods to find reservations by
 * user, those without payments, and reservations within a specific date range.
 */
@Repository
public interface EntertainmentReservationRepository
    extends JpaRepository<EntertainmentReservation, Integer> {
  List<EntertainmentReservation> findAllByUser(User user);

  List<EntertainmentReservation> findAllByPaymentIsNull();

  List<EntertainmentReservation> findAllByDateFromBetweenOrDateToBetween(
      Date date, Date date1, Date date2, Date date3);
}
