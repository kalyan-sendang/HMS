package com.java.hospitalmanagementsystem.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.List;

/**
 * Represents a room in a facility, including its number, access code, and room type. This entity is
 * mapped to the "Room" table in the database. It is used to manage and track rooms, their
 * reservations, and cleaning histories.
 */
@Data
@Entity
@Table(name = "Room")
public class Room {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @Column(name = "number", nullable = false)
  @Positive
  private int number;

  @Column(name = "access_code", nullable = false)
  @Positive
  private int accessCode;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "room_type_id", referencedColumnName = "id")
  private RoomType roomType;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "room")
  @JsonIgnore
  private List<RoomReservation> roomReservations;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "room")
  @JsonIgnore
  private List<CleaningHistory> cleaningHistories;

  public Room() {}

  /**
   * Constructs a new Room instance with the provided room details.
   *
   * @param number The room number.
   * @param accessCode The access code for the room.
   * @param roomType The type of the room.
   */
  public Room(int number, int accessCode, RoomType roomType) {
    this.number = number;
    this.accessCode = accessCode;
    this.roomType = roomType;
  }
}
