package com.api.payMyBuddy.service.mapper;

import com.api.payMyBuddy.model.entity.NetworkEntity;
import com.api.payMyBuddy.model.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MapperConnection {

    public List<UserEntity> getConnections(UserEntity userEntity) {
        List<UserEntity> connections = new ArrayList<>();
        for (NetworkEntity networkEntity : userEntity.getNetworkEntities())
        {
            connections.add(networkEntity.getNetworkPrimaryKey().getConnection());
        }
        return connections;
    }

}
