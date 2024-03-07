package com.java.hotelmanagementsystem.controllers;

import com.java.hotelmanagementsystem.responses.Response;
import com.java.hotelmanagementsystem.responses.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * This class serves as the AWS health check controller for monitoring the health and availability
 * of the application. It provides an endpoint specifically designed for AWS Elastic Load Balancer
 * (ELB) health checks. AWS ELB performs periodic health checks on this endpoint to determine the
 * health status of the application.
 */
@RestController
@RequestMapping("/api/v1/health")
@RequiredArgsConstructor
public class HealthController {

  @GetMapping("/test")
  public ResponseEntity<Response> getDishes() {
    return ResponseEntity.ok().body(new SuccessResponse<>(null));
  }

    @Autowired
    private DataSource dataSource;
    @GetMapping("/health-check")
    public String healthCheck() {
      try (Connection connection = dataSource.getConnection()) {
        return "Connected to PostgreSQL";
      } catch (SQLException e) {
        return "Failed to connect to PostgreSQL";
      }
    }
}
