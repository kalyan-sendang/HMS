package com.java.hotelmanagementsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.hotelmanagementsystem.models.Role;
import com.java.hotelmanagementsystem.models.User;
import com.java.hotelmanagementsystem.models.dto.User.DiscountChangeRequest;
import com.java.hotelmanagementsystem.models.dto.User.UserUpdateRequest;
import com.java.hotelmanagementsystem.repositories.RoleRepository;
import com.java.hotelmanagementsystem.repositories.UserRepository;
import com.java.hotelmanagementsystem.security.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = "spring.config.name=application-test")
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @MockBean
    private EmailService emailService;
    private User user, user1;

    @BeforeEach
    public void setup() {
        Role role = new Role(Role.RoleEnum.ROLE_USER);
        roleRepository.save(role);
        user = new User("kalyan", "sendang", "kalyan@gmail.com","9804993588",10,"Kalyan@123", role);
        user1 = new User("test", "test", "test@gmail.com", "980499567", 20, "Test@123", role);
        userRepository.save(user);
        userRepository.save(user1);
        Mockito.doNothing().when(emailService).sendConfirmationEmail(any(User.class));
        Authentication auth = new UsernamePasswordAuthenticationToken("kalyan@gmail.com", "password");
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(auth);
        SecurityContextHolder.setContext(securityContext);
    }
    @Test
    @DisplayName("Junit to get a User by userName")
    @WithMockUser(username = "kalyan@gmail.com", roles = "ADMIN")
    void givenFirstName_whenGetUser_thenReturnUser() throws Exception{
        mockMvc.perform(get("/api/v1/user/get-by-matching/kalyan"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id", is(1)))
                .andExpect(jsonPath("$.data[0].firstName", is("kalyan")));
    }
    @Test
    @DisplayName("Junit to get a User by userName")
    @WithMockUser(username = "kalyan@gmail.com", roles = "USER")
    void givenUserToken_whenGetUser_thenReturnUnauthorized () throws Exception{
        mockMvc.perform(get("/api/v1/user/get-by-matching/kalyan"))
                .andExpect(status().isUnauthorized());
    }
    @Test
    @DisplayName("Junit to get a User by Id")
    @WithMockUser(username = "kalyan@gmail.com", roles = "ADMIN")
    void givenUserId_whenGetById_thenReturnUser() throws Exception{
        mockMvc.perform(get("/api/v1/user/get/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", is(1)))
                .andExpect(jsonPath("$.data.firstName", is("kalyan")));

    }
    @Test
    @DisplayName("Junit to get all Users")
    @WithMockUser(username = "kalyan@gmail.com", roles = "ADMIN")
    void givenUser_whenGetAll_thenReturnAllUser()throws Exception{
        mockMvc.perform(get("/api/v1/user/get-all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id", is(1)))
                .andExpect(jsonPath("$.data[1].id", is(2)));
    }

    @Test
    @DisplayName("Junit to test emailVerificaton")
    @WithMockUser(username = "kalyan@gmail.com", roles = "ADMIN")
    void givenUser_whenEmailVerification_thenReturnVerified() throws Exception{
        mockMvc.perform(get("/api/v1/user/check-email-verification"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", is(false)));
    }

    @Test
    @DisplayName("Junit to update user")
    @WithMockUser(username = "kalyan@gmail.com", roles ="ADMIN")
    void givenUserObject_whenUpdate_thenReturnUpdatedUser() throws Exception{
        UserUpdateRequest userUpdateRequest =
                UserUpdateRequest.builder()
                        .firstName("test2")
                        .lastName("test2")
                        .email("test2@example.com")
                        .phone("987654321")
                        .discount(10)
                        .build();
        mockMvc.perform(put("/api/v1/user/update/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userUpdateRequest)))
                .andExpect(status().isOk());
        User user2 = userRepository.findById(1).get();
        assertEquals(10, user2.getDiscount());
        assertEquals("test2", user2.getFirstName());
        assertEquals("test2", user2.getLastName());
        assertEquals("987654321", user2.getPhone());
        assertEquals("test2@example.com", user2.getEmail());
    }

    @Test
    @DisplayName("JUnit to update the discount")
    @WithMockUser(username = "kalyan@gmail.com", roles="ADMIN")
    void givenDiscount_whenUpdate_thenReturnUpdatedUser()throws Exception{
        DiscountChangeRequest discountChangeRequest = new DiscountChangeRequest(15);
        mockMvc.perform(put("/api/v1/user/update-discount/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(discountChangeRequest)))
                .andExpect(status().isOk());
        User user2 = userRepository.findById(1).get();
        assertEquals(15,user2.getDiscount());
    }

    @Test
    @DisplayName("JUnit to update the discount for exception")
    @WithMockUser(username = "kalyan@gmail.com", roles="USER")
    void givenUserDiscount_whenUpdate_thenReturnUpdatedUser()throws Exception{
        DiscountChangeRequest discountChangeRequest = new DiscountChangeRequest(15);
        mockMvc.perform(put("/api/v1/user/update-discount/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(discountChangeRequest)))
                .andExpect(status().isUnauthorized());
    }
}
//given - precondition
//when - action or behaviour we are going to test
//then - verify the output

