package com.java.hospitalmanagementsystem.services.Implementation;

import com.java.hospitalmanagementsystem.models.Role;
import com.java.hospitalmanagementsystem.models.TokenType;
import com.java.hospitalmanagementsystem.models.User;
import com.java.hospitalmanagementsystem.models.dto.auth.AuthenticationRequest;
import com.java.hospitalmanagementsystem.models.dto.auth.AuthenticationResponse;
import com.java.hospitalmanagementsystem.models.dto.auth.RegisterRequest;
import com.java.hospitalmanagementsystem.repositories.RoleRepository;
import com.java.hospitalmanagementsystem.repositories.UserRepository;
import com.java.hospitalmanagementsystem.security.JwtService;
import com.java.hospitalmanagementsystem.security.SecurityTools;
import com.java.hospitalmanagementsystem.services.AuthenticationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Implementation for authentication services in the application.
 *
 * <p>This service defines methods for user registration, authentication, and password reset
 * functionalities.
 */
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImp implements AuthenticationService {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
//  private final EmailServic emailService;
  private final SecurityTools tools;

  /**
   * Registers a new user with the provided registration information.
   *
   * @param request The registration request containing user details.
   * @return An AuthenticationResponse containing the generated JWT token and user roles.
   * @throws IllegalArgumentException If the password or phone number is not valid, or if the email
   *     already exists.
   */
  @Transactional
  public AuthenticationResponse register(RegisterRequest request) {
    if (!tools.isValidPassword(request.getPassword())) {
      throw new IllegalArgumentException("Password is not valid");
    }
    if (!tools.isValidPhone(request.getPhoneNumber())) {
      throw new IllegalArgumentException("Phone is not valid");
    }
    userRepository
        .findByEmail(request.getEmail())
        .ifPresent(
            user -> {
              throw new IllegalArgumentException("Email already exists");
            });
    userRepository
        .findByPhone(request.getPhoneNumber())
        .ifPresent(
            user -> {
              throw new IllegalArgumentException("Phone already exists");
            });
    Role defaultRole =
        roleRepository
            .findByName(Role.RoleEnum.ROLE_USER)
            .orElseThrow(() -> new RuntimeException("User type not found"));

    User user =
        new User(
            request.getFirstName(),
            request.getLastName(),
            request.getEmail(),
            request.getPhoneNumber(),
            0,
            passwordEncoder.encode(request.getPassword()),
            defaultRole);
    User savedUser = userRepository.save(user);
    String jwtToken = jwtService.generateToken(user);
//    emailService.sendConfirmationEmail(user);
    jwtService.saveUserToken(savedUser, jwtToken, TokenType.TokenTypeEnum.ACCESS);
    Set<Role.RoleEnum> roles = new HashSet<>();
    roles.add(defaultRole.getName());
    return AuthenticationResponse.builder().token(jwtToken).roles(roles).build();
  }

  /**
   * Authenticates a user based on the provided email and password.
   *
   * @param request The authentication request containing user credentials.
   * @return An AuthenticationResponse containing the generated JWT token and user roles.
   * @throws UsernameNotFoundException If the provided email or password is invalid.
   */
  @Transactional
  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
      User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
      String jwtToken = jwtService.generateToken(user);
      jwtService.revokeAllUserTokens(user, TokenType.TokenTypeEnum.ACCESS);
      jwtService.saveUserToken(user, jwtToken, TokenType.TokenTypeEnum.ACCESS);
      Set<Role.RoleEnum> roles = new HashSet<>();
      user.getRoles().forEach(role -> roles.add(role.getName()));
      return AuthenticationResponse.builder().roles(roles).token(jwtToken).build();
    } catch (Exception e) {
      throw new UsernameNotFoundException("Invalid email or password");
    }
  }

//  @Override
//  @Transactional
//  public void resetPasswordRequest(PasswordResetRequest passwordResetRequest) {
//    Optional<User> user = userRepository.findByEmail(passwordResetRequest.getEmail());
//    if (user.isEmpty()) {
//      throw new IllegalArgumentException("User not found");
//    }
//    emailService.sendResetPasswordEmail(user.get());
//  }
//
//  @Override
//  @Transactional
//  public void resetPassword(String token, PasswordResetConfirmedRequest passwordRequest) {
//    if (token == null || token.isEmpty()) {
//      throw new IllegalArgumentException("Token is empty");
//    }
//    if (!tools.isValidPassword(passwordRequest.getPassword())) {
//      throw new IllegalArgumentException("Password is not valid");
//    }
//
//    final String userEmail;
//    userEmail = jwtService.extractUsername(token);
//    if (userEmail == null || userEmail.isEmpty()) {
//      throw new IllegalArgumentException("Token is invalid");
//    }
//    User user = userRepository.findByEmail(userEmail).orElseThrow();
//
//    if (!jwtService.isTokenValid(token, user) || !jwtService.isPasswordToken(token)) {
//      throw new IllegalArgumentException("Token is invalid");
//    } else if (jwtService.isTokenExpired(token)) {
//      throw new IllegalArgumentException("Token is expired");
//    } else {
//      jwtService.revokeAllUserTokens(user, TokenTypeEnum.RESET_PASSWORD);
//      user.setPassword(passwordEncoder.encode(passwordRequest.getPassword()));
//      userRepository.save(user);
//    }
//  }
}
