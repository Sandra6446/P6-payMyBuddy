package com.api.payMyBuddy.service;

import com.api.payMyBuddy.model.entity.ConnectionEntity;
import com.api.payMyBuddy.model.entity.UserEntity;
import com.api.payMyBuddy.model.front.User;
import com.api.payMyBuddy.model.repository.ConnectionEntityRepository;
import com.api.payMyBuddy.model.repository.UserEntityRepository;
import com.api.payMyBuddy.model.requestBody.ConnectionBody;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConnectionService {

    private static final Logger logger = LogManager.getLogger(ConnectionService.class);

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Autowired
    private ConnectionEntityRepository connectionEntityRepository;

    public ResponseEntity<String> createConnection(ConnectionBody connectionBody) {
        String message = "";
        Optional<UserEntity> userEntityOptional = userEntityRepository.findById(connectionBody.getUserEmail());
        if (userEntityOptional.isEmpty()) {
            message = "User not found";
            logger.error(message);
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            UserEntity userEntity = userEntityOptional.get();
            Optional<UserEntity> userEntityConnectionOptional = userEntityRepository.findById(connectionBody.getConnectionEmail());
            if (userEntityConnectionOptional.isEmpty()) {
                message = "Connection not found";
                logger.error(message);
                return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
            } else {
                UserEntity userEntityConnection = userEntityConnectionOptional.get();
                ConnectionEntity connectionEntity = new ConnectionEntity(userEntity, userEntityConnection);
                if (userEntity.getConnectionEntities().contains(connectionEntity)) {
                    message = "Connection already registered";
                    logger.error(message);
                    return new ResponseEntity<>(message, HttpStatus.CONFLICT);
                } else {
                    connectionEntityRepository.saveAndFlush(connectionEntity);
                    message = "Connection " + userEntityConnection.getEmail() + " added for " + userEntity.getEmail();
                    logger.info(message);
                    return new ResponseEntity<>(message, HttpStatus.CREATED);
                }
            }
        }
    }

    public ResponseEntity<Object> getConnections(String userEmail) {
        Optional<UserEntity> userEntityOptional = userEntityRepository.findById(userEmail);
        if (userEntityOptional.isEmpty()) {
            String message = "User not found";
            logger.error(message);
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            User user = new User(userEntityOptional.get());
            return ResponseEntity.ok(user.getConnections());
        }
    }
}
