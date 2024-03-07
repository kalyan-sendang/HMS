package com.java.hotelmanagementsystem.controllers;

import com.java.hotelmanagementsystem.models.dto.User.PasswordResetConfirmedRequest;
import com.java.hotelmanagementsystem.models.dto.User.PasswordResetRequest;
import com.java.hotelmanagementsystem.models.dto.auth.AuthenticationRequest;
import com.java.hotelmanagementsystem.models.dto.auth.RegisterRequest;
import com.java.hotelmanagementsystem.responses.Response;
import com.java.hotelmanagementsystem.responses.SuccessResponse;

import com.java.hotelmanagementsystem.security.EmailService;
import com.java.hotelmanagementsystem.services.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This class is the controller for authentication-related operations in the API. It handles
 * registration, authentication, email confirmation, password reset, and password reset
 * confirmation.
 */
@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
  private final AuthenticationService authenticationService;
  private final EmailService emailConfirmation;
  public AuthenticationController(AuthenticationService authenticationService, EmailService emailConfirmation) {
    this.authenticationService = authenticationService;
    this.emailConfirmation = emailConfirmation;
  }


  @PostMapping("/register")
  public ResponseEntity<Response> register(@RequestBody RegisterRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new SuccessResponse<>(authenticationService.register(request)));
  }

  @PostMapping("/authenticate")
  public ResponseEntity<Response> authenticate(@RequestBody AuthenticationRequest request) {
    return ResponseEntity.ok(new SuccessResponse<>(authenticationService.authenticate(request)));
  }

  @GetMapping("/email-confirmation/{token}")
  public ResponseEntity<Response> confirmEmail(@PathVariable String token) {
    emailConfirmation.confirmEmail(token);
    return ResponseEntity.ok(new SuccessResponse<>(null));
  }

  @PostMapping("/password-reset")
  public ResponseEntity<Response> passwordReset(
      @RequestBody @Valid PasswordResetRequest passwordResetRequest) {
    authenticationService.resetPasswordRequest(passwordResetRequest);
    return ResponseEntity.ok().body(new SuccessResponse<>(null));
  }

  @PostMapping("/password-reset-confirm/{token}")
  public ResponseEntity<Response> passwordResetConfirm(
      @PathVariable String token,
      @RequestBody @Valid PasswordResetConfirmedRequest passwordRequest) {
    authenticationService.resetPassword(token, passwordRequest);
    return ResponseEntity.ok().body(new SuccessResponse<>(null));
  }
}
