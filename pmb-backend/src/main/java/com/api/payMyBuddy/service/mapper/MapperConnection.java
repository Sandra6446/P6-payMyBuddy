package com.api.payMyBuddy.service.mapper;

import com.api.payMyBuddy.model.entity.ConnectionEntity;
import com.api.payMyBuddy.model.entity.UserEntity;
import com.api.payMyBuddy.model.front.Connection;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Organizes user connection
 */
@Component
public class MapperConnection {

    /**
     * Gets all connections of a user, organized by name
     * @param userEntity : The user entity containing the connection list to be sorted
     * @return A connection list, sorted by name
     */
    public List<Connection> getConnections(UserEntity userEntity) {
        List<Connection> connections = new ArrayList<>();
        for (ConnectionEntity connectionEntity:userEntity.getConnectionEntities()) {
            connections.add(new Connection(connectionEntity));
        }
        connections.sort(Comparator.comparing(Connection::getName));
        return connections;
    }
}
