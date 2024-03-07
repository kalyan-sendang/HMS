package com.java.hotelmanagementsystem.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

/**
 * Represents the cleaning history of a room. This entity is mapped to the "Cleaning_History" table
 * in the database.
 */
@Data
@Entity
@Table(name = "Cleaning_History")
public class CleaningHistory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "room_id", referencedColumnName = "id")
  private Room room;

  @Column(name = "cleaned_at", nullable = false)
  private Timestamp cleanedAt;

  public CleaningHistory() {}

  public CleaningHistory(Room room) {
    this.room = room;
  }

  public CleaningHistory(Room room, Timestamp cleanedAt) {
    this.room = room;
    this.cleanedAt = cleanedAt;
  }
}
