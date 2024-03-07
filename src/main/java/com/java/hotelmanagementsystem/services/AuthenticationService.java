package com.java.hotelmanagementsystem.services;

import com.java.hotelmanagementsystem.models.dto.User.PasswordResetConfirmedRequest;
import com.java.hotelmanagementsystem.models.dto.User.PasswordResetRequest;
import com.java.hotelmanagementsystem.models.dto.auth.AuthenticationRequest;
import com.java.hotelmanagementsystem.models.dto.auth.AuthenticationResponse;
import com.java.hotelmanagementsystem.models.dto.auth.RegisterRequest;

/**
 * Interface for authentication services in the application.
 *
 * <p>This interface defines methods for user registration, authentication, and password reset
 * functionalities.
 */
public interface AuthenticationService {
  AuthenticationResponse register(RegisterRequest request);

  AuthenticationResponse authenticate(AuthenticationRequest request);

  void resetPasswordRequest(PasswordResetRequest passwordResetRequest);

  void resetPassword(String token, PasswordResetConfirmedRequest passwordRequest);
}
