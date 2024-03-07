package com.java.hotelmanagementsystem.models.dto.auth;


import com.java.hotelmanagementsystem.models.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Represents an authentication response containing an authentication token and user roles. This
 * class is typically used to convey the result of a successful authentication, providing an
 * authentication token and the roles associated with the authenticated user.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
  private String token;
  private Set<Role.RoleEnum> roles;
}
