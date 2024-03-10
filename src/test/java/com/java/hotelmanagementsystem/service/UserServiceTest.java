package com.java.hotelmanagementsystem.service;

import com.java.hotelmanagementsystem.models.EntertainmentReservation;
import com.java.hotelmanagementsystem.models.Role;
import com.java.hotelmanagementsystem.models.RoomReservation;
import com.java.hotelmanagementsystem.models.User;
import com.java.hotelmanagementsystem.models.dto.User.DiscountChangeRequest;
import com.java.hotelmanagementsystem.models.dto.User.PhoneChangeRequest;
import com.java.hotelmanagementsystem.models.dto.User.UserDto;
import com.java.hotelmanagementsystem.repositories.UserRepository;
import com.java.hotelmanagementsystem.security.EmailService;
import com.java.hotelmanagementsystem.security.JwtService;
import com.java.hotelmanagementsystem.security.SecurityTools;
import com.java.hotelmanagementsystem.services.Implementation.UserServiceImp;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.BDDMockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;


import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.*;

import static com.java.hotelmanagementsystem.models.Role.RoleEnum.ROLE_USER;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private EmailService emailService;
    @Mock
    private SecurityTools securityTools;
    @Mock
    private JwtService jwtService;
    @InjectMocks
    private UserServiceImp userService;

    private User user, user1;

    @BeforeEach
    public void setup() {
        Set<Role> newRoles = new HashSet<>();
        newRoles.add(new Role(ROLE_USER));
        //given - precondition
        user = new User();
        user.setId(1);
        user.setFirstName("kalyan");
        user.setLastName("sendang");
        user.setEmail("kalyan@gmail.com");
        user.setPhone("9804993544");
        user.setPassword("Kalyan@123");
        user.setDiscount(10);
        user.setVerified(null);
        user.setRoles(newRoles);

        user1 = new User();
        user1.setId(2);
        user1.setFirstName("karan");
        user1.setLastName("sendang");
        user1.setEmail("Karan@gmail.com");
        user1.setPhone("9804993546");
        user1.setPassword("Karan@123");
        user1.setDiscount(17);
        user1.setVerified(null);
        user1.setRoles(newRoles);
    }

    @Test
    @DisplayName("Junit to get all users")
    void givenUserObject_whenSave_thenReturnAllUser() {
        //given - precondition
        userRepository.save(user);
        userRepository.save(user1);
        given(userRepository.findAll()).willReturn(List.of(user, user1));
        //when - action or behaviour we are going to test
        List<UserDto> userList = userService.getAll();

        //then - verify the output
        assertThat(userList).isNotNull().hasSize(2);

    }

    @Test
    @DisplayName("Junit to get a users by id")
    void givenUserId_whenSave_thenReturnAllUser() {
        //given - precondition
        userRepository.save(user);
        userRepository.save(user1);
        given(userRepository.findById(1)).willReturn(Optional.of(user));
        given(userRepository.findById(2)).willReturn(Optional.of(user1));
        //when - action or behaviour we are going to test
        UserDto userDto1 = userService.getById(user.getId());
        UserDto userDto2 = userService.getById(user1.getId());
        //then - verify the output
        assertThat(userDto1).isNotNull();
        assertThat(userDto2).isNotNull();
    }

    @Test
    @DisplayName("Junit to check email varification")
    void givenUserObject_whenVarification_thenReturnVerification() {
        //given - precondition
        userRepository.save(user);
        user.setVerified(new Timestamp(System.currentTimeMillis()));
        given(securityTools.retrieveUserData()).willReturn(user);
        //when - action or behaviour we are going to test
        boolean result = userService.checkEmailVerification();
        //then - verify the output
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Junit to update password")
    void givenUserIdAndPassword_whenUpdate_thenReturnUpdatedUser() {
        //given - precondition
        userRepository.save(user);
        PhoneChangeRequest phoneChangeRequest = new PhoneChangeRequest("9804993532");
        given(securityTools.isValidPhone(anyString())).willReturn(true);
        given(securityTools.retrieveUserData()).willReturn(user);
        //when - action or behaviour we are going to test
        userService.updatePhone(phoneChangeRequest);
        //then - verify the output
        assertThat(user.getPhone()).isEqualTo("9804993532");
    }

    @Test
    @DisplayName("Junit to update Discount")
    void givenUserDiscount_whenUpdate_thenReturnUpdatedUser() {
        //given - precondition
        userRepository.save(user);
        int id = user.getId();
        DiscountChangeRequest discountChangeRequest = new DiscountChangeRequest(20);
        given(userRepository.findById(id)).willReturn(Optional.of(user));
        //when - action or behaviour we are going to test
        userService.updateDiscount(id, discountChangeRequest);
        //then - verify the output
        assertThat(user.getDiscount()).isEqualTo(20);
    }

    @Test
    @DisplayName("JUnit to get user Reservation Count")
    void givenUser_whenCount_thenReturnUserReservationCount() {
        //given - precondition
        userRepository.save(user);
        user.setRoomReservations(Arrays.asList(new RoomReservation(), new RoomReservation()));
        given(securityTools.retrieveUserData()).willReturn(user);
        //when - action or behaviour we are going to test
        int count = userService.getUserRoomReservationsCount();
        //then - verify the output
        assertThat(count).isEqualTo(2);
    }

    //given - precondition
    //when - action or behaviour we are going to test
    //then - verify the output
    @Test
    @DisplayName("Junit to get User Entertainment Reservations Count ")
    void givenUser_whenCount_thenReturnUserEntertainmentCount() {
        //given - precondition
        userRepository.save(user);
        user.setEntertainmentReservations(Arrays.asList(new EntertainmentReservation(), new EntertainmentReservation()));
        given(securityTools.retrieveUserData()).willReturn(user);
        //when - action or behaviour we are going to test
        int count = userService.getUserEntertainmentReservationsCount();
        //then - verify the output
        assertThat(count).isEqualTo(2);
    }
}
