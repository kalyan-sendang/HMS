package com.java.hospitalmanagementsystem.controllers;

import com.java.hospitalmanagementsystem.models.dto.cleaning.CleanRoomRequest;
import com.java.hospitalmanagementsystem.responses.Response;
import com.java.hospitalmanagementsystem.responses.SuccessResponse;
import com.java.hospitalmanagementsystem.services.CleaningService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * This class serves as the controller for managing cleaning-related operations in the API. It
 * handles tasks related to cleaning rooms and provides access control through role-based
 * authorization. @RestController indicates that this class is a Spring MVC controller.
 */
@RestController
@RequestMapping("/api/v1/cleaning")
@RequiredArgsConstructor
public class CleaningController {

  private final CleaningService cleaningService;

  @GetMapping("/getRoomsToClean")
  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLEANER')")
  public ResponseEntity<Response> getRoomsToClean() {
    return ResponseEntity.ok().body(new SuccessResponse<>(cleaningService.getRoomsToClean()));
  }

  @PostMapping("/cleanRoom")
  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLEANER')")
  public ResponseEntity<Response> cleanRoom(@RequestBody CleanRoomRequest cleanRoomRequest) {
    cleaningService.cleanRoom(cleanRoomRequest.getRoomId());
    return ResponseEntity.ok().body(new SuccessResponse<>(null));
  }
}
