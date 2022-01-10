package com.api.payMyBuddy.service;

import com.api.payMyBuddy.exceptions.AlreadyInDatabaseException;
import com.api.payMyBuddy.exceptions.NotFoundInDatabaseException;
import com.api.payMyBuddy.model.entity.ConnectionEntity;
import com.api.payMyBuddy.model.entity.ConnectionPrimaryKey;
import com.api.payMyBuddy.model.entity.UserEntity;
import com.api.payMyBuddy.model.front.BankAccount;
import com.api.payMyBuddy.model.front.Connection;
import com.api.payMyBuddy.model.front.User;
import com.api.payMyBuddy.model.repository.ConnectionEntityRepository;
import com.api.payMyBuddy.model.repository.UserEntityRepository;
import com.api.payMyBuddy.service.mapper.MapperConnection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class ConnectionServiceTest {

    private static String userEmail;
    private static UserEntity userEntity;
    private static UserEntity userEntityConnection;
    private static ConnectionEntity connectionEntity;
    private static Connection connection;
    @MockBean
    private UserEntityRepository userEntityRepository;
    @MockBean
    private ConnectionEntityRepository connectionEntityRepository;
    @MockBean
    private MapperConnection mapperConnection;
    private ConnectionService connectionService;

    @BeforeAll
    private static void setUp() {
        // User
        userEmail = "user@email.com";
        BankAccount bankAccount = new BankAccount("ma banque", "iban", "bic");
        User user = new User(userEmail, "pwd", null, "Monsieur", "Test", 50, bankAccount);

        // UserEntity
        userEntity = new UserEntity(user);

        // UserEntityConnection
        userEntityConnection = new UserEntity(user);
        userEntityConnection.setEmail("connectionEmail@email.com");

        // ConnectionEntity
        ConnectionPrimaryKey connectionPrimaryKey = new ConnectionPrimaryKey();
        connectionPrimaryKey.setUserEntity(userEntity);
        connectionPrimaryKey.setContactEntity(userEntityConnection);
        connectionEntity = new ConnectionEntity(connectionPrimaryKey);

        // Connection
        connection = new Connection(connectionEntity);
    }

    @BeforeEach
    private void setUpPerTest() {
        connectionService = new ConnectionService(userEntityRepository, connectionEntityRepository, mapperConnection);
    }

    @Test
    void createConnection() {

        Mockito.when(userEntityRepository.findByEmail(userEmail)).thenReturn(Optional.of(userEntity));
        Mockito.when(userEntityRepository.findByEmail("connectionEmail@email.com")).thenReturn(Optional.of(userEntityConnection));
        Assertions.assertEquals(new ResponseEntity<>("Connection added", HttpStatus.CREATED), connectionService.createConnection(connection));

        // Test to add a connection of an invalid user
        Mockito.when(userEntityRepository.findByEmail(userEmail)).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundInDatabaseException.class, () -> connectionService.createConnection(connection));

        // Test to add an invalid connection
        Mockito.when(userEntityRepository.findByEmail(userEmail)).thenReturn(Optional.of(userEntity));
        Mockito.when(userEntityRepository.findByEmail("connectionEmail@email.com")).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundInDatabaseException.class, () -> connectionService.createConnection(connection));

        // Test to add a connection already registered
        userEntity.setConnectionEntities(List.of(connectionEntity));
        Mockito.when(userEntityRepository.findByEmail(userEmail)).thenReturn(Optional.of(userEntity));
        Mockito.when(userEntityRepository.findByEmail("connectionEmail@email.com")).thenReturn(Optional.of(userEntityConnection));
        Assertions.assertThrows(AlreadyInDatabaseException.class, () -> connectionService.createConnection(connection));
    }

    @Test
    void getConnections() {
        // Test to get connections of an invalid user
        Mockito.when(userEntityRepository.findByEmail(userEmail)).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundInDatabaseException.class, () -> connectionService.getConnections(userEmail));

        // Test to get connections of a user
        Mockito.when(userEntityRepository.findByEmail(userEmail)).thenReturn(Optional.of(userEntity));
        Mockito.when(mapperConnection.getConnections(userEntity)).thenReturn(List.of(connection));
        Assertions.assertEquals(ResponseEntity.ok(List.of(connection)), connectionService.getConnections(userEmail));
    }
}