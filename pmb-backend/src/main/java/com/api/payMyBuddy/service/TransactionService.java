package com.api.payMyBuddy.service;

import com.api.payMyBuddy.controller.UserController;
import com.api.payMyBuddy.model.entity.TransactionEntity;
import com.api.payMyBuddy.model.entity.UserEntity;
import com.api.payMyBuddy.model.front.User;
import com.api.payMyBuddy.model.repository.TransactionEntityRepository;
import com.api.payMyBuddy.model.repository.UserEntityRepository;
import com.api.payMyBuddy.model.requestBody.TransactionBody;
import com.api.payMyBuddy.service.mapper.MapperTransaction;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionService {
    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Autowired
    private TransactionEntityRepository transactionEntityRepository;

    @Autowired
    private MapperTransaction mapperTransaction;

    @Autowired
    private UserService userService;

    public ResponseEntity<Object> getMyTransactions(String user_email) {
        String message = "";
        Optional<UserEntity> userEntityOptional = userEntityRepository.findByEmail(user_email);
        if (userEntityOptional.isEmpty()) {
            message = "User not found";
            logger.error(message);
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            User user = new User(userEntityOptional.get());
            FilterProvider filterProvider = new SimpleFilterProvider()
                    .addFilter("userFilter", SimpleBeanPropertyFilter.filterOutAllExcept("debits"));
            MappingJacksonValue connections = new MappingJacksonValue(user);
            connections.setFilters(filterProvider);
            return ResponseEntity.ok(connections);
        }
    }

    public ResponseEntity<Object> getAllTransactions(String user_email) {
        String message = "";
        Optional<UserEntity> userEntityOptional = userEntityRepository.findByEmail(user_email);
        if (userEntityOptional.isEmpty()) {
            message = "User not found";
            logger.error(message);
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            User user = new User(userEntityOptional.get());
            return ResponseEntity.ok(mapperTransaction.getAllTransactions(user));
        }
    }

    public ResponseEntity<String> createTransaction(TransactionBody transactionBody) {
        String message = "";
        Optional<UserEntity> userEntityOptional = userEntityRepository.findByEmail(transactionBody.getUserEmail());
        if (userEntityOptional.isEmpty()) {
            message = "User not found";
            logger.error(message);
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            UserEntity userEntity = userEntityOptional.get();
            Optional<UserEntity> userEntityConnectionOptional = userEntityRepository.findByEmail(transactionBody.getConnectionEmail());
            if (userEntityConnectionOptional.isEmpty()) {
                message = "Connection not found";
                logger.error(message);
                return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
            } else {
                UserEntity userEntityConnection = userEntityConnectionOptional.get();
                TransactionEntity transactionEntity = new TransactionEntity(userEntity,userEntityConnection,transactionBody);
                if (userEntity.getDebits().contains(transactionEntity)) {
                    message = "Transaction already registered";
                    logger.error(message);
                    return new ResponseEntity<>(message, HttpStatus.CONFLICT);
                } else {
                    transactionEntityRepository.saveAndFlush(transactionEntity);
                    message = "Transaction added for " + userEntity.getEmail();
                    logger.info(message);
                    return new ResponseEntity<>(message, HttpStatus.CREATED);
                }
            }
        }
    }
}
