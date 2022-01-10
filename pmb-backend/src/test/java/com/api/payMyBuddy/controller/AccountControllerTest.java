package com.api.payMyBuddy.controller;

import com.api.payMyBuddy.exceptions.NotFoundInDatabaseException;
import com.api.payMyBuddy.model.repository.ConnectionEntityRepository;
import com.api.payMyBuddy.model.repository.TransactionEntityRepository;
import com.api.payMyBuddy.model.repository.UserEntityRepository;
import com.api.payMyBuddy.service.AccountService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
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

@WebMvcTest(controllers = AccountController.class)
@ActiveProfiles("test")
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @MockBean
    private UserEntityRepository userEntityRepository;

    @MockBean
    private TransactionEntityRepository transactionEntityRepository;

    @MockBean
    private ConnectionEntityRepository connectionEntityRepository;

    private static String userEmail;

    @BeforeAll
    private static void setUp() {
        userEmail = "userEmail@email.com";
    }

    @Test
    void addMoney() throws Exception {

        // Test to add money to a user
        String response = "Account updated";

        Mockito.when(accountService.addMoney(userEmail, 20)).thenReturn(ResponseEntity.ok(response));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/account")
                        .param("email", userEmail)
                        .param("amount", String.valueOf(20))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(response));

        // Test to add money with just a space instead of email
        mockMvc.perform(MockMvcRequestBuilders.post("/api/account")
                        .param("email", " ")
                        .param("amount", String.valueOf(20))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Error in request body"));

        // Test to add a negative amount to catch an exception
        Mockito.when(accountService.addMoney(userEmail, 20)).thenThrow(new NotFoundInDatabaseException("User not found"));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/account")
                        .param("email", userEmail)
                        .param("amount", String.valueOf(20))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("User not found"));
    }


}


