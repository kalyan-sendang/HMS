package com.java.hotelmanagementsystem.repository;

import com.java.hotelmanagementsystem.models.Role;
import com.java.hotelmanagementsystem.models.User;
import com.java.hotelmanagementsystem.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.*;

import static com.java.hotelmanagementsystem.models.Role.RoleEnum.ROLE_USER;


@DataJpaTest
public class UserRepoTest {
    @Autowired
    private UserRepository userRepository;

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

    @DisplayName("Junit to save user")
    @Test
    @Transactional
    void givenUserObject_whenSave_thenReturnUser(){
        Set<Role> newRoles = new HashSet<>();
        newRoles.add(new Role(ROLE_USER));
        //given - precondition
        //when - action or behaviour we are going to test
        User savedUser = userRepository.save(user);

        //then - verify the output
        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isPositive();
    }

    @DisplayName("Junit to get all user")
    @Test
    void givenSavedUsersObject_whenRetrieve_thenReturnAllSavedUsers(){
        //given- precondition
        userRepository.save(user);
        userRepository.save(user1);
        //when - action or behaviour we are going to test
        List<User> userList =userRepository.findAll();
        //then - verify the output
        Assertions.assertThat(userList).isNotNull().hasSize(2);
    }

    @DisplayName("Junit to retreive one user")
    @Test
    void givenSavedUserId_whenRetreive_thenReturnASavedUser() {
        //given - precondition
        userRepository.save(user);
        //when - action or behaviour we are going to test
        User savedUser = userRepository.findById(user.getId()).get();
        //then - verify the output
        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isEqualTo(1);
    }

    @DisplayName("Junit to update User")
    @Test
    void givenUserObject_whenUpdate_thenReturnUpdatedUser() {
        //given - precondition
        userRepository.save(user);
        //when - action or behavior we are going to test
        User newUser =userRepository.findById(user.getId()).get();
        newUser.setFirstName("kalyann");
        newUser.setLastName("limbu");
        newUser.setEmail("kalyann@gmail.com");
        newUser.setPassword("Kalyann@123");

        User user2 = userRepository.save(newUser);

        //then - verify the output

        Assertions.assertThat(newUser.getFirstName()).isEqualTo("kalyann");
        Assertions.assertThat(newUser.getLastName()).isEqualTo("limbu");
        Assertions.assertThat(newUser.getEmail()).isEqualTo("kalyann@gmail.com");
        Assertions.assertThat(newUser.getPassword()).isEqualTo("Kalyann@123");

    }

    @DisplayName("Junit ti delete a User")
    @Test
    void givenUserId_whenDelete_thenReturnVoid(){
        //given - precondition
        userRepository.save(user);
        //when - action or behavior we are going to test
        userRepository.delete(user);
        Optional<User> optionalUser = userRepository.findById(user.getId());
        //then - verify the output
        Assertions.assertThat(optionalUser).isEmpty();

    }
}
