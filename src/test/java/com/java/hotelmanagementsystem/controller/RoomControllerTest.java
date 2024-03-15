package com.java.hotelmanagementsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.hotelmanagementsystem.models.*;
import com.java.hotelmanagementsystem.repositories.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@SpringBootTest(properties = "spring.config.name=application-test")
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RoomControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private RoomTypeRepository roomTypeRepository;
    @Autowired
    private RoomReservationRepository roomReservationRepository;

    @BeforeEach
    public void setup() {
        Role role = new Role(Role.RoleEnum.ROLE_USER);
        roleRepository.save(role);
        User user = new User("test", "test", "test@example.com", "123456789", 0, "test", role);
        userRepository.save(user);
        RoomType roomType = new RoomType(4, 100, 100);
        roomTypeRepository.save(roomType);
        Room room1 = new Room(1, 1234, roomType);
        Room room2 = new Room(2, 1235, roomType);
        Room room3 = new Room(3, 1236, roomType);
        Room room4 = new Room(4, 1237, roomType);
        Room room5 = new Room(5, 1238, roomType);
        roomRepository.saveAll(List.of(room1, room2, room3, room4, room5));
        RoomReservation roomReservation =
                new RoomReservation(
                        Date.valueOf("2028-10-10"),
                        Date.valueOf("2028-10-12"),
                        new Timestamp(System.currentTimeMillis()),
                        room1,
                        user);
        roomReservationRepository.save(roomReservation);
    }

    @Test
    @DisplayName("Junit to test a Room")
    @WithMockUser(username = "test@example.com", roles = "ADMIN")
    void givenRoomId_whenGetRoomById_thenReturnRoom() throws Exception {
        mockMvc
                .perform(get("/api/v1/room/get/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.number", is(1)));
    }

    @Test
    @DisplayName("Junit to save Room")
    @WithMockUser(username = "test@example.com", roles = "ADMIN")
    void givenRoomObject_whenSaveRoom_thenReturnRoomObject() throws Exception {
        Room room = new Room(5, 1239, roomTypeRepository.findById(1).get());
        mockMvc
                .perform(post("/api/v1/room/add")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(room)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.number", is(5)));
        Assertions.assertEquals(6, roomRepository.findAll().size());
    }
    @Test
    @DisplayName("Junit to get all Room")
    void givenRoomObject_whenGetAll_thenReturnAllRoom() throws Exception {
        mockMvc
                .perform(get("/api/v1/room/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].number", is(1)))
                .andExpect(jsonPath("$.data[1].number", is(2)));
    }

    @Test
    @DisplayName("Junit to delete Room")
    @WithMockUser(username = "test@example.com", roles = "ADMIN")
    void givenRoomId_whenDelete_thenReturnNull() throws Exception{
        mockMvc
                .perform(delete("/api/v1/room/delete/1")
                        .contentType("application/json"))
                .andExpect(status().isOk());
                Assertions.assertEquals(4, roomRepository.findAll().size());
    }
    @Test
    @DisplayName("Junit to get Room Availabilty True")
    @WithMockUser(username = "test@example.com", roles = "ADMIN")
    void givenRoomDate_whenGetAvailable_thenReturnTrue() throws Exception{
        mockMvc
                .perform(get("/api/v1/room/room-available/1/2024-03-15/2024-03-16"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", is(true)));
    }

    @Test
    @DisplayName("Junit to get Room Availability False")
    void givenRoomDate_whenGetAvailable_thenReturnFalse() throws Exception {
        mockMvc
                .perform(get("/api/v1/room/room-available/1/2028-10-10/2028-10-12"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", is(false)));
    }
    @Test
    @DisplayName("Junit to get Room Availability and Type not Occupied")
    void givenRoomDate_whenGetRoomAndTypeOccupied_thenReturnRoomAvailable() throws Exception {
        mockMvc
                .perform(get("/api/v1/room/available/2028-10-15/2028-10-18/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()", is(5)));
    }

    @Test
    @DisplayName("Junit to get Room Availability and Type not Occupied")
    void givenRoomDate_whenGetRoomAndTypeOccupied_thenReturnRoomNotAvailable() throws Exception {
        mockMvc
                .perform(get("/api/v1/room/available/2028-10-10/2028-10-12/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()", is(4)));
    }
    @Test
    @DisplayName("Junit to get all Room Types")
    void givenRoomObj_whenGetAllTypes_thenReturnAllRoomTypes() throws Exception {
        mockMvc
                .perform(get("/api/v1/room/all-room-type"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()", is(1)));
    }

    @Test
    @DisplayName("Junit to test room by availability and Occupancy")
    void getAllRoomTypesByAvailabilityAndOccupancy() throws Exception {
        mockMvc
                .perform(get("/api/v1/room/available-room-type/2028-10-15/2028-10-18/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()", is(1)));
    }
    @Test
    @DisplayName("Junit to get Room by Availability and Occupancy")
    void getAllRoomTypesByAvailabilityAndOccupancyToBigOccupancy() throws Exception {
        mockMvc
                .perform(get("/api/v1/room/available-room-type/2028-10-15/2028-10-18/6"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()", is(0)));
    }

    @Test
    @DisplayName("Junit to get Room by availability and not occupied")
    void getAllRoomByAvailabilityNotOccupied() throws Exception {
        mockMvc
                .perform(get("/api/v1/room/available-rooms/2028-10-15/2028-10-18"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()", is(5)));
    }

    @Test
    @DisplayName("Junit to get Room by Availability and occupied")
    void getAllRoomByAvailabilityOccupied() throws Exception {
        mockMvc
                .perform(get("/api/v1/room/available-rooms/2028-10-10/2028-10-12"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()", is(4)));
    }

    @Test
    @DisplayName("Junit to get update Price of room")
    @WithMockUser(username = "test@example.com", roles = "ADMIN")
    void givenPrice_whenUpdatePrice_thenReturnRoomWithUpdatePrice() throws Exception {
        List<RoomType> roomTypes = roomTypeRepository.findAll();
        roomTypes.get(0).setPrice(200);
        mockMvc
                .perform(
                        patch("/api/v1/room/update-price")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(roomTypes)))
                .andExpect(status().isOk());
        assertEquals(200, roomTypeRepository.findById(1).get().getPrice());
    }

}
