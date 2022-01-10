package com.api.payMyBuddy.service.mapper;

import com.api.payMyBuddy.model.entity.ConnectionEntity;
import com.api.payMyBuddy.model.entity.UserEntity;
import com.api.payMyBuddy.model.front.Connection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class MapperConnectionTest {

    private static UserEntity userEntity;
    private final MapperConnection mapperConnection = new MapperConnection();

    @BeforeAll
    private static void setUp() {
        userEntity = new UserEntity(1, "user@email.com", "User", "Test", "pwd", 20, "Bank", "Iban", "Bic", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        UserEntity contact1 = new UserEntity(2, "connection1@email.com", "John", "Smith", "pwd", 50, "Bank", "Iban", "Bic", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        UserEntity contact2 = new UserEntity(3, "connection2@email.com", "Jane", "Smith", "pwd", 50, "Bank", "Iban", "Bic", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        ConnectionEntity connectionEntity1 = new ConnectionEntity(userEntity,contact1);
        ConnectionEntity connectionEntity2 = new ConnectionEntity(userEntity,contact2);
        userEntity.setConnectionEntities(List.of(connectionEntity1,connectionEntity2));
    }

    @Test
    void getConnections() {
        Connection connection1 = new Connection(userEntity.getConnectionEntities().get(0));
        Connection connection2 = new Connection(userEntity.getConnectionEntities().get(1));
        List<Connection> connections = new ArrayList<>(List.of(connection2,connection1));
        Assertions.assertIterableEquals(connections, mapperConnection.getConnections(userEntity));
    }
}