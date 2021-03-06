package com.api.payMyBuddy.service;

import com.api.payMyBuddy.exceptions.AlreadyInDatabaseException;
import com.api.payMyBuddy.exceptions.NotFoundInDatabaseException;
import com.api.payMyBuddy.model.entity.ConnectionEntity;
import com.api.payMyBuddy.model.entity.UserEntity;
import com.api.payMyBuddy.model.front.Connection;
import com.api.payMyBuddy.model.repository.ConnectionEntityRepository;
import com.api.payMyBuddy.model.repository.UserEntityRepository;
import com.api.payMyBuddy.service.mapper.MapperConnection;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Manages the current user connections
 */
@Service
@AllArgsConstructor
public class ConnectionService {

    private static final Logger logger = LogManager.getLogger(ConnectionService.class);

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Autowired
    private ConnectionEntityRepository connectionEntityRepository;

    @Autowired
    private MapperConnection mapperConnection;

    /**
     * Adds a contact
     *
     * @param connection : The contact to create
     * @return Status CREATED,"Connection added" if the operation succeeds, otherwise the reason of the failure
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ResponseEntity<String> createConnection(Connection connection) throws RuntimeException {
        Optional<UserEntity> userEntityOptional = userEntityRepository.findByEmail(connection.getUserEmail());
        if (userEntityOptional.isEmpty()) {
            logger.error(String.format("User %s not found", connection.getUserEmail()));
            throw new NotFoundInDatabaseException("User not found");
        } else {
            UserEntity userEntity = userEntityOptional.get();
            Optional<UserEntity> userEntityConnectionOptional = userEntityRepository.findByEmail(connection.getConnectionEmail());
            if (userEntityConnectionOptional.isEmpty()) {
                logger.error(String.format("Connection %s not found", connection.getConnectionEmail()));
                throw new NotFoundInDatabaseException("Connection not found");
            } else {
                UserEntity userEntityConnection = userEntityConnectionOptional.get();
                ConnectionEntity connectionEntity = new ConnectionEntity(userEntity, userEntityConnection);
                if (userEntity.getConnectionEntities().contains(connectionEntity)) {
                    logger.error("Connection already registered");
                    throw new AlreadyInDatabaseException("Connection already registered");
                } else {
                    connectionEntityRepository.saveAndFlush(connectionEntity);
                    logger.info(String.format("User %s : Connection %s added", connection.getUserEmail(), connection));
                    return new ResponseEntity<>("Connection added", HttpStatus.CREATED);
                }
            }
        }
    }

    /**
     * Gets the connection list of current user
     * @param userEmail : The current user email
     * @return Status OK, with the connection list if the operation succeeds, otherwise the reason of the failure
     */
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public ResponseEntity<Object> getConnections(String userEmail) throws RuntimeException {
        Optional<UserEntity> userEntityOptional = userEntityRepository.findByEmail(userEmail);
        if (userEntityOptional.isEmpty()) {
            logger.error(String.format("User %s not found", userEmail));
            throw new NotFoundInDatabaseException("User not found");
        } else {
            UserEntity userEntity = userEntityOptional.get();
            List<Connection> connections = mapperConnection.getConnections(userEntity);
            logger.info("Connections are found");
            return ResponseEntity.ok(connections);
        }
    }
}
