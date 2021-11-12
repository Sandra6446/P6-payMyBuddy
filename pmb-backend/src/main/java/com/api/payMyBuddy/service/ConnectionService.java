package com.api.payMyBuddy.service;

import com.api.payMyBuddy.controller.UserController;
import com.api.payMyBuddy.model.entity.NetworkEntity;
import com.api.payMyBuddy.model.entity.UserEntity;
import com.api.payMyBuddy.model.repository.ConnectionEntityRepository;
import com.api.payMyBuddy.model.repository.UserEntityRepository;
import com.api.payMyBuddy.service.mapper.MapperConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConnectionService {

    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Autowired
    private ConnectionEntityRepository connectionEntityRepository;

    @Autowired
    private MapperConnection mapperConnection;

    public ResponseEntity<Object> createConnectionEntity(String userEmail, String connectionEmail) {
        Optional<UserEntity> userEntityOptional = userEntityRepository.findById(userEmail);
        if (userEntityOptional.isEmpty()) {
            logger.error("User not found");
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        } else {
            UserEntity userEntity = userEntityOptional.get();
            Optional<UserEntity> connectionOptional = userEntityRepository.findById(connectionEmail);
            if (connectionOptional.isEmpty()) {
                logger.error("User not found");
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            } else {
                UserEntity connection = connectionOptional.get();
                if (mapperConnection.getConnections(userEntity).contains(connection)) {
                    logger.error("Connection already registered");
                    return new ResponseEntity<>("Connection already registered", HttpStatus.CONFLICT);
                } else {
                    NetworkEntity networkEntity = new NetworkEntity(userEntity,connection);
                    connectionEntityRepository.saveAndFlush(networkEntity);
                    return new ResponseEntity<>("Connection " + connectionEmail + " added for " + userEmail, HttpStatus.CREATED);
                }
            }
        }
    }

}
