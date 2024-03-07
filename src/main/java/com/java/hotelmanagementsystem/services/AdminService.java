package com.java.hotelmanagementsystem.services;

import com.java.hotelmanagementsystem.models.Role;
import com.java.hotelmanagementsystem.models.Token;
import com.java.hotelmanagementsystem.models.TokenType;

public interface AdminService {
    Role addRole(String role);
    TokenType addTokenType(String token);
}
