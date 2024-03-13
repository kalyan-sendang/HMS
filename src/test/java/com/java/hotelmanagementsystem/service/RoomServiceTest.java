package com.java.hotelmanagementsystem.service;

import com.java.hotelmanagementsystem.models.Room;
import com.java.hotelmanagementsystem.models.RoomType;
import com.java.hotelmanagementsystem.repositories.RoomRepository;
import com.java.hotelmanagementsystem.repositories.RoomTypeRepository;
import com.java.hotelmanagementsystem.services.Implementation.RoomServiceImp;
import static org.assertj.core.api.Assertions.*;

import com.java.hotelmanagementsystem.util.Tools;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {
    @Mock
    private RoomRepository roomRepository;
    @Mock
    private RoomTypeRepository roomTypeRepository;
    @InjectMocks
    private RoomServiceImp roomService;

    @Test
    @DisplayName("Junit to get room by id")
    void givenRoomId_whenGetById_thenReturnARoom() {
        //given - precondition
        Room room = new Room();
        room.setId(1);
        given(roomRepository.findById(1)).willReturn(Optional.of(room));
        //when - action or behaviour we are going to test
        Room result = roomService.getById(1);
        //then - verify the output
        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("Junit to add room")
    void givenRoomObject_whenAdd_thenReturnRoom(){
        //given - precondition
        Room room = new Room();
        given(roomRepository.save(room)).willReturn(room);
        //when - action or behaviour we are going to test
        Room result = roomService.add(room);
        //then - verify the output
        assertThat(room).isEqualTo(result);
    }

    @Test
    @DisplayName("Junit to delete room")
    void givenRoomObject_whenDelete_thenReturnNull(){
        //given - precondition
        Room room = new Room();
        room.setId(1);
        given(roomRepository.findById(1)).willReturn(Optional.of(room));
        //when - action or behaviour we are going to test
        roomService.delete(1);
        //then - verify the output
        verify(roomRepository, times(1)).deleteById(1);
    }

    @Test
    @DisplayName("Junit to get all room")
    void givenRoomObject_whenGetAll_thenReturnAllRooms(){
        //given - precondition
        Room room = new Room();
        Room room1 = new Room();
        List<Room> rooms = Arrays.asList(room, room1);
        given(roomRepository.findAll()).willReturn(rooms);
        //when - action or behaviour we are going to test
        List<Room> roomList = roomService.getAll();
        //then - verify the output
        assertThat(roomList).hasSize(2);
    }

    @Test
    @DisplayName("Junit to room is available")
    void givenDetail_whenIsAvailable_thenReturnRoomAvailable(){
        //given - precondition
        int roomId =1;
        String from = "2024-03-14";
        String to = "2024-03-16";
        Date dateFromSql = Tools.parseDate(from);
        Date dateToSql = Tools.parseDate(to);
        given(roomRepository.isRoomAvailable(roomId,dateFromSql, dateToSql)).willReturn(true);
        //when - action or behaviour we are going to test
        boolean result = roomService.isRoomAvailable(roomId,from,to);
        //then - verify the output
        assertThat(result).isTrue();
        verify(roomRepository, times(1)).isRoomAvailable(roomId,dateFromSql,dateToSql);
    }
    @Test
    @DisplayName("Junit to get all tyoes of room")
    void givenRoomObject_whenGetAll_thenReturnAllRoomTypes(){
        //given - precondition
        RoomType roomType = new RoomType();
        RoomType roomType1 = new RoomType();
        List<RoomType> roomTypes = Arrays.asList(roomType,roomType1);
        given(roomTypeRepository.findAll()).willReturn(roomTypes);
        //when - action or behaviour we are going to test
        List<RoomType> roomTypeList = roomService.getAllRoomTypes();
        //then - verify the output
        assertThat(roomTypeList).hasSize(2);
    }
    @Test
    @DisplayName("Junit to update price")
    void givenRoomPrice_whenUpdatePrice_thenReturnUpdateRoom(){
        //given - precondition
        RoomType roomType = new RoomType();
        roomType.setId(1);
        given(roomTypeRepository.findById(1)).willReturn(Optional.of(roomType));
        //when - action or behaviour we are going to test
        List<RoomType> roomTypes = List.of(roomType);
        roomService.updatePrices(roomTypes);
        //then - verify the output
        verify(roomTypeRepository, times(1)).save(roomType);
    }
}

