package com.api.payMyBuddy.controller;

import com.api.payMyBuddy.model.front.Connection;
import com.api.payMyBuddy.model.requestBody.ConnectionBody;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(controllers = ConnectionController.class)
class ConnectionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConnectionService connectionService;

    private static String userEmail;

    @BeforeAll
    private static void setUp() {
        userEmail = "userEmail@email.com";
    }

    @Test
    void addConnection() throws Exception {

        // Test to add a ConnectionBody
        ConnectionBody connectionBody = new ConnectionBody(userEmail, "connectionEmail@email.com");
        String response = "Connection added";

        Mockito.when(connectionService.createConnection(ArgumentMatchers.any(ConnectionBody.class))).thenReturn(new ResponseEntity<>(response, HttpStatus.CREATED));
        mockMvc.perform(MockMvcRequestBuilders.post("/connection")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(connectionBody.toString()))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string(response));

        // Test to add an empty ConnectionBody
        ConnectionBody connectionBodyEmpty = new ConnectionBody("","");
        mockMvc.perform(MockMvcRequestBuilders.post("/connection")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(connectionBodyEmpty.toString()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        // Test to add current user as connection
        ConnectionBody connectionBodySame = new ConnectionBody(userEmail,userEmail);
        mockMvc.perform(MockMvcRequestBuilders.post("/connection")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(connectionBodySame.toString()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void getConnections() throws Exception {

        Connection connection = new Connection(userEmail, "UserTest");

        // Test to get all connections of a user
        Mockito.when(connectionService.getConnections(ArgumentMatchers.anyString())).thenReturn(ResponseEntity.ok(connection));
        mockMvc.perform(get("/connection/{email}",userEmail))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(connection.toString()));

        // Test to get connections of an empty user
        String userEmpty = " ";
        mockMvc.perform(get("/connection/{email}",userEmpty))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}