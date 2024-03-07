package com.java.hotelmanagementsystem.services;

import com.java.hotelmanagementsystem.models.Payment;
import com.java.hotelmanagementsystem.models.dto.payment.CreatePayment;

/**
 * Interface for managing payment processing in the application.
 *
 * <p>This interface provides methods for calculating payment amounts, creating reservations, and
 * processing actual payments. It handles various aspects of payment from computation to final
 * transaction creation.
 */
public interface PaymentService {
  int getPaymentAmount(CreatePayment createPayment);

  int createReservation(CreatePayment createPayment);

  Payment createPayment(int amount);
}
