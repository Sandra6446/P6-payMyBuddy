/*package com.api.payMyBuddy.service;

import com.api.payMyBuddy.model.entity.ConnectionEntity;
import com.api.payMyBuddy.model.entity.ConnectionPrimaryKey;
import com.api.payMyBuddy.model.entity.UserEntity;
import com.api.payMyBuddy.model.front.BankAccount;
import com.api.payMyBuddy.model.front.Connection;
import com.api.payMyBuddy.model.front.User;
import com.api.payMyBuddy.model.repository.ConnectionEntityRepository;
import com.api.payMyBuddy.model.repository.UserEntityRepository;
import com.api.payMyBuddy.model.requestBody.ConnectionBody;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class ConnectionServiceTest {

    @MockBean
    private UserEntityRepository userEntityRepository;

    @MockBean
    private ConnectionEntityRepository connectionEntityRepository;

    private ConnectionService connectionService;

    private static String userEmail;
    private static User user;
    private static UserEntity userEntity;
    private static UserEntity userEntityConnection;
    private static ConnectionEntity connectionEntity;

    @BeforeAll
    private static void setUp() {
        // User
        userEmail = "user@email.com";
        BankAccount bankAccount = new BankAccount("ma banque", "iban", "bic");
        user = new User("user@email.com", "newPwd", "newPwd", "Prenom", "Nom", 20.5, bankAccount, null, null, null);

        // UserEntity
        userEntity = new UserEntity(user);

        // UserEntityConnection
        userEntityConnection = new UserEntity(user);
        userEntityConnection.setEmail("connectionEmail@email.com");

        // ConnectionEntity
        ConnectionPrimaryKey connectionPrimaryKey = new ConnectionPrimaryKey();
        connectionPrimaryKey.setUserEntity(userEntity);
        connectionPrimaryKey.setUserEntityConnection(userEntityConnection);
        connectionEntity = new ConnectionEntity(connectionPrimaryKey);
    }

    @BeforeEach
    private void setUpPerTest() {
        connectionService = new ConnectionService(userEntityRepository, connectionEntityRepository);
    }

    @Test
    void createConnection() throws Exception {
        // Test to add a ConnectionBody
        ConnectionBody connectionBody = new ConnectionBody(userEmail, "connectionEmail@email.com");
        String response = "Connection " + "connectionEmail@email.com" + " added for " + userEmail;

        Mockito.when(userEntityRepository.findById(ArgumentMatchers.anyString())).thenReturn(Optional.of(userEntity));
        Mockito.when(connectionEntityRepository.saveAndFlush(ArgumentMatchers.any(ConnectionEntity.class))).thenReturn(connectionEntity);
        Assertions.assertEquals(new ResponseEntity<>(response, HttpStatus.CREATED), connectionService.createConnection(connectionBody));

        // Test to add a connection of an invalid user
        response = "User not found";
        Mockito.when(userEntityRepository.findById(ArgumentMatchers.anyString())).thenReturn(Optional.empty());
        Assertions.assertEquals(new ResponseEntity<>(response, HttpStatus.NOT_FOUND), connectionService.createConnection(connectionBody));        // Test to add a connection of an invalid user

        // Test to add an invalid connection
        response = "Connection not found";
        Mockito.when(userEntityRepository.findById(userEmail)).thenReturn(Optional.of(userEntity));
        Mockito.when(userEntityRepository.findById("connectionEmail@email.com")).thenReturn(Optional.empty());
        Assertions.assertEquals(new ResponseEntity<>(response, HttpStatus.NOT_FOUND), connectionService.createConnection(connectionBody));

        // Test to add a connection already registered
        response = "Connection already registered";
        List<ConnectionEntity> connectionEntities = new ArrayList<>();
        connectionEntities.add(connectionEntity);
        userEntity.setConnectionEntities(connectionEntities);
        Mockito.when(userEntityRepository.findById(userEmail)).thenReturn(Optional.of(userEntity));
        Mockito.when(userEntityRepository.findById("connectionEmail@email.com")).thenReturn(Optional.of(userEntityConnection));
        Assertions.assertEquals(new ResponseEntity<>(response, HttpStatus.CONFLICT), connectionService.createConnection(connectionBody));
    }

    @Test
    void getConnections() {
        // Test to get connections of an invalid user
        String response = response = "User not found";
        Mockito.when(userEntityRepository.findById(ArgumentMatchers.anyString())).thenReturn(Optional.empty());
        Assertions.assertEquals(new ResponseEntity<>(response, HttpStatus.NOT_FOUND), connectionService.getConnections(userEmail));

        // Test to get connections of a user
        List<ConnectionEntity> connectionEntities = new ArrayList<>();
        connectionEntities.add(connectionEntity);
        userEntity.setConnectionEntities(connectionEntities);
        Connection connection = new Connection(connectionEntity);
        List<Connection> connections = new ArrayList<>();
        connections.add(connection);
        response = "{\"connections\": "+connections+"}";
        Mockito.when(userEntityRepository.findById(userEmail)).thenReturn(Optional.of(userEntity));
        Mockito.when(userEntityRepository.findById("connectionEmail@email.com")).thenReturn(Optional.of(userEntityConnection));
        Assertions.assertEquals(ResponseEntity.ok(response), connectionService.getConnections(userEmail));
    }
}*/