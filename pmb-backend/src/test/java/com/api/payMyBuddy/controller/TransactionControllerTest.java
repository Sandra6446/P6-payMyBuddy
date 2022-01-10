package com.api.payMyBuddy.controller;

import com.api.payMyBuddy.exceptions.NotFoundInDatabaseException;
import com.api.payMyBuddy.model.front.Transaction;
import com.api.payMyBuddy.model.repository.ConnectionEntityRepository;
import com.api.payMyBuddy.model.repository.TransactionEntityRepository;
import com.api.payMyBuddy.model.repository.UserEntityRepository;
import com.api.payMyBuddy.service.TransactionService;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TransactionController.class)
@ActiveProfiles("test")
class TransactionControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private UserEntityRepository userEntityRepository;

    @MockBean
    private TransactionEntityRepository transactionEntityRepository;

    @MockBean
    private ConnectionEntityRepository connectionEntityRepository;

    @MockBean
    TransactionService transactionService;

    private static String userEmail;
    private static Transaction transaction;

    @BeforeAll
    private static void setUp() throws JsonProcessingException {
        userEmail = "userEmail@email.com";
        transaction = new Transaction(userEmail, "connection@email.com", "Monsieur TEST", "My transaction", Transaction.Type.DEBIT, 100, LocalDateTime.now());
    }

    @Test
    void getAllTransactions() throws Exception {

        // Test to get all transactions of a user

        Mockito.when(transactionService.getAllTransactions(ArgumentMatchers.anyString())).thenReturn(ResponseEntity.ok(transaction.toString()));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/transaction/{email}", userEmail))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(transaction.toString()));

        // Test to get transactions of an empty user
        String userEmpty = " ";
        mockMvc.perform(MockMvcRequestBuilders.get("/api/transaction/{email}", userEmpty))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        // Test to get transactions of an unregistered user to catch an exception
        Mockito.when(transactionService.getAllTransactions(ArgumentMatchers.anyString())).thenThrow(new NotFoundInDatabaseException("User not found"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/transaction/{email}", userEmail))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("User not found"));
    }

    @Test
    void getMyTransactions() throws Exception {

        // Test to get all transactions made by a user
        Mockito.when(transactionService.getMyTransactions(ArgumentMatchers.anyString())).thenReturn(ResponseEntity.ok(transaction.toString()));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/transaction/{email}/myTransactions", userEmail))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(transaction.toString()));

        // Test to get all transactions made by an empty user
        String userEmpty = " ";
        mockMvc.perform(MockMvcRequestBuilders.get("/api/transaction/{email}/myTransactions", userEmpty))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        // Test to get transactions of an unregistered user to catch an exception
        Mockito.when(transactionService.getMyTransactions(ArgumentMatchers.anyString())).thenThrow(new NotFoundInDatabaseException("User not found"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/transaction/{email}/myTransactions", userEmail))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("User not found"));
    }

    @Test
    void addTransaction() throws Exception {

        // Test to add a TransactionBody
        Transaction transactionBody = new Transaction(userEmail, "connectionEmail@email.com", "Madame Test", "My transactions", Transaction.Type.DEBIT, 10, LocalDateTime.now());
        String response = "Transaction added";

        Mockito.when(transactionService.createTransaction(ArgumentMatchers.any(Transaction.class))).thenReturn(new ResponseEntity<>(response, HttpStatus.CREATED));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/transaction")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(transactionBody.toString()))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string(response));

        // Test to add an empty TransactionBody
        Transaction transactionEmpty = new Transaction();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/transaction")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(transactionEmpty.toString()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        // Test to add a transaction with current user
        Transaction transactionSame = new Transaction(userEmail, userEmail, "Madame Test", "My transactions", Transaction.Type.DEBIT, 10, LocalDateTime.now());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/transaction")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(transactionSame.toString()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        // Test to add a transaction with an unregistered connection to catch an exception
        Mockito.when(transactionService.createTransaction(ArgumentMatchers.any(Transaction.class))).thenThrow(new NotFoundInDatabaseException("Connection not found"));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/transaction")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(transaction.toString()))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Connection not found"));

    }
}