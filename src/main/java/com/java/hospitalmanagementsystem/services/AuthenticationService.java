package com.java.hospitalmanagementsystem.services;

import com.java.hospitalmanagementsystem.models.dto.auth.AuthenticationRequest;
import com.java.hospitalmanagementsystem.models.dto.auth.AuthenticationResponse;
import com.java.hospitalmanagementsystem.models.dto.auth.RegisterRequest;
import org.springframework.stereotype.Service;

/**
 * Interface for authentication services in the application.
 *
 * <p>This interface defines methods for user registration, authentication, and password reset
 * functionalities.
 */
public interface AuthenticationService {
  AuthenticationResponse register(RegisterRequest request);

  AuthenticationResponse authenticate(AuthenticationRequest request);

//  void resetPasswordRequest(PasswordResetRequest passwordResetRequest);

//  void resetPassword(String token, PasswordResetConfirmedRequest passwordRequest);
}
