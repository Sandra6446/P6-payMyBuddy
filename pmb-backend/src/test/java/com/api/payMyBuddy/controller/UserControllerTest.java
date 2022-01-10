package com.api.payMyBuddy.controller;

import com.api.payMyBuddy.exceptions.AlreadyInDatabaseException;
import com.api.payMyBuddy.exceptions.NotFoundInDatabaseException;
import com.api.payMyBuddy.model.front.BankAccount;
import com.api.payMyBuddy.model.front.User;
import com.api.payMyBuddy.model.repository.ConnectionEntityRepository;
import com.api.payMyBuddy.model.repository.TransactionEntityRepository;
import com.api.payMyBuddy.model.repository.UserEntityRepository;
import com.api.payMyBuddy.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@ActiveProfiles("test")
class UserControllerTest {

    private static User user;
    @Autowired
    MockMvc mockMvc;
    @MockBean
    UserService userService;
    @MockBean
    private UserEntityRepository userEntityRepository;
    @MockBean
    private TransactionEntityRepository transactionEntityRepository;
    @MockBean
    private ConnectionEntityRepository connectionEntityRepository;

    @BeforeAll
    private static void setUp() {
        BankAccount bankAccount = new BankAccount("banque", "iban", "bic");
        user = new User("user@email.com", "pwd", "pwd", "Prenom", "Nom", 50, bankAccount);
    }

    @Test
    void getUser() throws Exception {
        // Test to get a user
        Mockito.when(userService.getUser(ArgumentMatchers.anyString())).thenReturn(ResponseEntity.ok(user.toString()));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/{email}", "user@email.com"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(user.toString()));

        // Test to get a user with an empty email
        String emailEmpty = " ";
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/{email}", emailEmpty))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        // Test to get a user unregistered to catch an exception
        Mockito.when(userService.getUser(ArgumentMatchers.anyString())).thenThrow(new NotFoundInDatabaseException("User not found"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/{email}", user.getEmail()))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("User not found"));
    }

    @Test
    void updateUser() throws Exception {
        // Test to update a User
        Mockito.when(userService.updateUser(ArgumentMatchers.anyString(), ArgumentMatchers.any(User.class))).thenReturn(ResponseEntity.ok("User updated"));
        mockMvc.perform(MockMvcRequestBuilders.put("/api/user/{email}", user.getEmail())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(user.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("User updated"));

        // Test to update an empty User
        User userEmpty = new User();
        mockMvc.perform(MockMvcRequestBuilders.put("/api/user/{email}", user.getEmail())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(userEmpty.toString()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        // Test to update a user with an email already in database to catch an exception
        Mockito.when(userService.updateUser(ArgumentMatchers.anyString(), ArgumentMatchers.any(User.class))).thenThrow(new AlreadyInDatabaseException("New email not valid, please choose another email"));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/user/{email}", user.getEmail())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(user.toString()))
                .andExpect(status().isConflict())
                .andExpect(MockMvcResultMatchers.content().string("New email not valid, please choose another email"));
    }

}