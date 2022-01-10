package com.api.payMyBuddy.controller;

import com.api.payMyBuddy.exceptions.NotFoundInDatabaseException;
import com.api.payMyBuddy.model.front.Connection;
import com.api.payMyBuddy.model.repository.ConnectionEntityRepository;
import com.api.payMyBuddy.model.repository.TransactionEntityRepository;
import com.api.payMyBuddy.model.repository.UserEntityRepository;
import com.api.payMyBuddy.service.ConnectionService;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ConnectionController.class)
@ActiveProfiles("test")
class ConnectionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConnectionService connectionService;

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
    void addConnection() throws Exception {

        // Test to add a ConnectionBody
        Connection connection = new Connection(userEmail, "connectionEmail@email.com", "Madame Test");
        String response = "Connection added";

        Mockito.when(connectionService.createConnection(ArgumentMatchers.any(Connection.class))).thenReturn(new ResponseEntity<>(response, HttpStatus.CREATED));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/connection")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(connection.toString()))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string(response));

        // Test to add an empty ConnectionBody
        Connection connectionEmpty = new Connection();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/connection")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(connectionEmpty.toString()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Error in request body"));

        // Test to add current user as connection
        Connection connectionSame = new Connection(userEmail, userEmail, "Madame Test");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/connection")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(connectionSame.toString()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Error in request body"));

        // Test to add an unregistered connection to catch an exception
        Mockito.when(connectionService.createConnection(ArgumentMatchers.any(Connection.class))).thenThrow(new NotFoundInDatabaseException("Connection not found"));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/connection")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(connection.toString()))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Connection not found"));
    }

    @Test
    void getConnections() throws Exception {

        Connection connection = new Connection(userEmail, "UserTest", "Mr Test");

        // Test to get all connections of a user
        Mockito.when(connectionService.getConnections(ArgumentMatchers.anyString())).thenReturn(ResponseEntity.ok(connection));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/connection/{email}", userEmail)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(connection.toString()));

        // Test to get connections of an empty user
        String userEmpty = " ";
        mockMvc.perform(MockMvcRequestBuilders.get("/api/connection/{email}", userEmpty)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Error in request body"));

        // Test to get connections of an unregistered user to catch an exception
        Mockito.when(connectionService.getConnections(ArgumentMatchers.anyString())).thenThrow(new NotFoundInDatabaseException("User not found"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/connection/{email}", userEmail)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("User not found"));
    }
}