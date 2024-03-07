package com.java.hotelmanagementsystem.services.Implementation;

import com.java.hotelmanagementsystem.models.CleaningHistory;
import com.java.hotelmanagementsystem.models.Room;
import com.java.hotelmanagementsystem.models.dto.cleaning.CleaningResponse;
import com.java.hotelmanagementsystem.repositories.CleaningHistoryRepository;
import com.java.hotelmanagementsystem.repositories.RoomRepository;
import com.java.hotelmanagementsystem.services.CleaningService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation for managing cleaning services in the application.
 *
 * <p>This service provides methods for retrieving a list of rooms that require cleaning and for
 * marking a specific room as cleaned.
 */
@Service
@RequiredArgsConstructor
public class CleaningServiceImp implements CleaningService {

  private final RoomRepository roomRepository;
  private final CleaningHistoryRepository cleaningHistoryRepository;

  @Override
  public List<CleaningResponse> getRoomsToClean() {
    LocalDate currentDate = LocalDate.now();
    List<CleaningResponse> cleaningResponses = new ArrayList<>();
    roomRepository
        .findActiveReservationRoomsNotCleanedToday(currentDate)
        .forEach(
            room -> cleaningResponses.add(
                CleaningResponse.builder()
                    .roomId(room.getId())
                    .roomNumber(room.getNumber())
                    .roomType(room.getRoomType().getId())
                    .accessCode(room.getAccessCode())
                    .build()));
    return cleaningResponses;
  }

  @Override
  public void cleanRoom(int roomId) {
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    Room room = roomRepository.findById(roomId).orElseThrow();
    CleaningHistory cleaningHistory = new CleaningHistory(room, timestamp);
    cleaningHistoryRepository.save(cleaningHistory);
  }
}
