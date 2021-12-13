package com.api.payMyBuddy.service.mapper;

import com.api.payMyBuddy.model.entity.ConnectionEntity;
import com.api.payMyBuddy.model.entity.UserEntity;
import com.api.payMyBuddy.model.front.Connection;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class MapperConnection {

    public List<Connection> getConnections(UserEntity userEntity) {
        List<Connection> connections = new ArrayList<>();
        for (ConnectionEntity connectionEntity:userEntity.getConnectionEntities()) {
            connections.add(new Connection(connectionEntity));
        }
        connections.sort(Comparator.comparing(Connection::getName));
        return connections;
    }
}
