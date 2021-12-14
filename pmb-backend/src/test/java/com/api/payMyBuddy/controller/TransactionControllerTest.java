package com.api.payMyBuddy.controller;

import com.api.payMyBuddy.model.front.Transaction;
import com.api.payMyBuddy.model.requestBody.TransactionBody;
import com.api.payMyBuddy.service.TransactionService;
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

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(controllers = TransactionController.class)
class TransactionControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TransactionService transactionService;

    private static String userEmail;
    private static Transaction transaction;

    @BeforeAll
    private static void setUp() {
        userEmail = "userEmail@email.com";
        transaction = new Transaction(userEmail,"connection@email.com", "Monsieur TEST","My transaction", Transaction.Type.DEBIT, 100, LocalDateTime.now());
    }

    @Test
    void getAllTransactions() throws Exception {

        // Test to get all transactions of a user
        Mockito.when(transactionService.getAllTransactions(ArgumentMatchers.anyString())).thenReturn(ResponseEntity.ok(transaction));
        mockMvc.perform(get("/transaction/{email}", userEmail))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(transaction.toString()));

        // Test to get transactions of an empty user
        String userEmpty = " ";
        mockMvc.perform(get("/transaction/{email}", userEmpty))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    void getTransactions() throws Exception {

        // Test to get all transactions made by a user
        Mockito.when(transactionService.getMyTransactions(ArgumentMatchers.anyString())).thenReturn(ResponseEntity.ok(transaction));
        mockMvc.perform(get("/transaction/{email}/myTransactions", userEmail))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(transaction.toString()));

        // Test to get all transactions made by an empty user
        String userEmpty = " ";
        mockMvc.perform(get("/transaction/{email}/myTransactions", userEmpty))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void addTransaction() throws Exception {

        // Test to add a TransactionBody
        TransactionBody transactionBody = new TransactionBody(userEmail, "connectionEmail@email.com","My transactions",10);
        String response = "Transaction added";

        Mockito.when(transactionService.createTransaction(ArgumentMatchers.any(Transaction.class))).thenReturn(new ResponseEntity<>(response, HttpStatus.CREATED));
        mockMvc.perform(MockMvcRequestBuilders.post("/transaction")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(transactionBody.toString()))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string(response));

        // Test to add an empty TransactionBody
        TransactionBody transactionBodyEmpty = new TransactionBody();
        mockMvc.perform(MockMvcRequestBuilders.post("/transaction")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(transactionBodyEmpty.toString()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        // Test to add a transaction with current user
        TransactionBody transactionBodySame = new TransactionBody(userEmail,userEmail,"Description",10);
        mockMvc.perform(MockMvcRequestBuilders.post("/transaction")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(transactionBodySame.toString()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}