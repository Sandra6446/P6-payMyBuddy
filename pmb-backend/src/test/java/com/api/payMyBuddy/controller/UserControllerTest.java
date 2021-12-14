package com.api.payMyBuddy.controller;

import com.api.payMyBuddy.model.front.BankAccount;
import com.api.payMyBuddy.model.front.User;
import com.api.payMyBuddy.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    private static User user;
    private static BankAccount bankAccount;

    @BeforeAll
    private static void setUp() {
        bankAccount = new BankAccount("ma banque", "iban", "bic");
        user = new User("user@email.com", "newPwd", "newPwd", "Prenom", "Nom", -20.5,bankAccount);
    }

    @Test
    void addUser() throws Exception {

        // Test to add a User
        String response = "User added";
        Mockito.when(userService.createUser(ArgumentMatchers.any(User.class))).thenReturn(new ResponseEntity<>(response, HttpStatus.CREATED));
        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(user.toString()))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string(response));

        // Test to add an empty User
        User userEmpty = new User();
        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(userEmpty.toString()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void getUser() throws Exception {
        // Test to get a user
        Mockito.when(userService.getUser(ArgumentMatchers.anyString())).thenReturn(ResponseEntity.ok(user.toString()));
        mockMvc.perform(get("/user/{email}", "user@email.com"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(user.toString()));

        // Test to get a user with an empty email
        String userEmpty = " ";
        mockMvc.perform(get("/user/{email}", userEmpty))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /*
    @Test
    void updateUser() throws Exception {
        // Test to update a User
        String response = "User updated";

        Mockito.when(userService.updateUser(ArgumentMatchers.any(UserProfile.class))).thenReturn(ResponseEntity.ok(response));
        mockMvc.perform(MockMvcRequestBuilders.put("/user")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(userProfile.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(response));

        // Test to update an empty User
        UserProfile profileEmpty = new UserProfile();
        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(profileEmpty.toString()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    */
}