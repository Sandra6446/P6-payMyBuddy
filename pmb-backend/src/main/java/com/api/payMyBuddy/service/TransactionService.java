package com.api.payMyBuddy.service;

import com.api.payMyBuddy.controller.UserController;
import com.api.payMyBuddy.model.entity.TransactionEntity;
import com.api.payMyBuddy.model.entity.UserEntity;
import com.api.payMyBuddy.model.front.Transaction;
import com.api.payMyBuddy.model.repository.TransactionEntityRepository;
import com.api.payMyBuddy.model.repository.UserEntityRepository;
import com.api.payMyBuddy.service.mapper.MapperTransaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
            return ResponseEntity.ok(mapperTransaction.getDebits(userEntityOptional.get()));
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
            UserEntity userEntity = userEntityOptional.get();
            return ResponseEntity.ok(mapperTransaction.getAllTransactions(userEntity));
        }
    }

    public ResponseEntity<String> createTransaction(Transaction transaction) {
        String message = "";
        Optional<UserEntity> userEntityOptional = userEntityRepository.findByEmail(transaction.getUserEmail());
        if (userEntityOptional.isEmpty()) {
            message = "User not found";
            logger.error(message);
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            UserEntity userEntity = userEntityOptional.get();
            Optional<UserEntity> userEntityConnectionOptional = userEntityRepository.findByEmail(transaction.getConnectionEmail());
            if (userEntityConnectionOptional.isEmpty()) {
                message = "Connection not found";
                logger.error(message);
                return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
            } else {
                UserEntity userEntityConnection = userEntityConnectionOptional.get();
                TransactionEntity transactionEntity = new TransactionEntity(userEntity,userEntityConnection,transaction);
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
