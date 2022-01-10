package com.api.payMyBuddy.controller;

import com.api.payMyBuddy.exceptions.AlreadyInDatabaseException;
import com.api.payMyBuddy.model.front.BankAccount;
import com.api.payMyBuddy.model.front.User;
import com.api.payMyBuddy.model.repository.ConnectionEntityRepository;
import com.api.payMyBuddy.model.repository.TransactionEntityRepository;
import com.api.payMyBuddy.model.repository.UserEntityRepository;
import com.api.payMyBuddy.security.jwt.JwtUtils;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthController.class)
@ActiveProfiles("test")
class AuthControllerTest {

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
    @MockBean
    private AuthenticationManager authenticationManager;
    @MockBean
    private JwtUtils jwtUtils;

    @BeforeAll
    private static void setUp() {
        BankAccount bankAccount = new BankAccount("banque", "iban", "bic");
        user = new User("user@email.com", "pwd", "pwd", "Prenom", "Nom", 50, bankAccount);
    }

    @Test
    void registerUser() throws Exception {
        // Test to register a user
        Mockito.when(userService.createUser(ArgumentMatchers.any(User.class))).thenReturn(new ResponseEntity<>("User created", HttpStatus.CREATED));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(user.toString()))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string("User created"));

        // Test to add a registered user
        Mockito.when(userService.createUser(ArgumentMatchers.any(User.class))).thenThrow(new AlreadyInDatabaseException("User already in database"));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(user.toString()))
                .andExpect(status().isConflict())
                .andExpect(MockMvcResultMatchers.content().string("User already in database"));

        // Test to add an invalid user
        Mockito.when(userService.createUser(ArgumentMatchers.any(User.class))).thenReturn(new ResponseEntity<>("The two passwords are different", HttpStatus.BAD_REQUEST));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(user.toString()))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("The two passwords are different"));

        User emptyUser = new User();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(emptyUser.toString()))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Error in request body"));
    }
}
