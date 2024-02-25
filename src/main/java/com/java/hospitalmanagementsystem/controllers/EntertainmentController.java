package com.java.hospitalmanagementsystem.controllers;

import com.java.hospitalmanagementsystem.models.Entertainment;
import com.java.hospitalmanagementsystem.models.EntertainmentType;
import com.java.hospitalmanagementsystem.models.Room;
import com.java.hospitalmanagementsystem.responses.Response;
import com.java.hospitalmanagementsystem.responses.SuccessResponse;
import com.java.hospitalmanagementsystem.services.EntertainmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class serves as the controller for managing entertainment-related operations in the API. It
 * provides endpoints for retrieving entertainment types, available entertainment elements within a
 * specified time frame, and updating entertainment prices. Access to certain operations is
 * restricted to administrators based on role-based authorization.
 */
@RestController
@RequestMapping("/api/v1/entertainment")
@RequiredArgsConstructor
public class EntertainmentController {

  private final EntertainmentService entertainmentService;

  @GetMapping("/getTypes")
  public ResponseEntity<Response> getTypes() {
    return ResponseEntity.ok()
        .body(new SuccessResponse<>(entertainmentService.getAllEntertainmentTypes()));
  }

  @PostMapping("/add")
  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  public ResponseEntity<Response> add(@RequestBody @Valid Entertainment entertainment) {
    Entertainment newEntertainment = entertainmentService.add(entertainment);
    return ResponseEntity.ok().body(new SuccessResponse<>(newEntertainment));
  }
  @GetMapping("/get/{id}")
  public ResponseEntity<Response> getById(@PathVariable Integer id) {
    Entertainment entertainment  = entertainmentService.getById(id);
    return ResponseEntity.ok().body(new SuccessResponse<>(entertainment));
  }

  @GetMapping("/getAll")
  public ResponseEntity<Response> getAll() {
    Iterable<Entertainment> entertainments = entertainmentService.getAll();
    return ResponseEntity.ok().body(new SuccessResponse<>(entertainments));
  }
  /**
   * Retrieves a list of available entertainment elements of a specific type within a specified time
   * frame.
   *
   * @param entertainmentType The type of entertainment elements to retrieve.
   * @param dateFrom The start date of the time frame for availability checking (yyyy-MM-dd).
   * @param timeFrom The start time of the time frame for availability checking (HH:mm:ss).
   * @param dateTo The end date of the time frame for availability checking (yyyy-MM-dd).
   * @param timeTo The end time of the time frame for availability checking (HH:mm:ss).
   * @return A ResponseEntity with a SuccessResponse containing a list of available entertainment
   *     elements that match the criteria.
   */
  @GetMapping("/getElements/{entertainmentType}/{dateFrom}/{timeFrom}/{dateTo}/{timeTo}")
  public ResponseEntity<Response> getAvailableElements(
      @PathVariable String entertainmentType,
      @PathVariable String dateFrom,
      @PathVariable String timeFrom,
      @PathVariable String dateTo,
      @PathVariable String timeTo) {
    return ResponseEntity.ok()
        .body(
            new SuccessResponse<>(
                entertainmentService.getAllEntertainmentElementsByAvailableDate(
                    entertainmentType, dateFrom, timeFrom, dateTo, timeTo)));
  }

  @PatchMapping("/update-price")
  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  public ResponseEntity<Response> updatePrice(
      @RequestBody List<EntertainmentType> entertainmentTypes) {
    entertainmentService.updatePrices(entertainmentTypes);
    return ResponseEntity.ok().body(new SuccessResponse<>(null));
  }
}
