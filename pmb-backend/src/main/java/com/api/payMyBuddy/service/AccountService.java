package com.api.payMyBuddy.service;

import com.api.payMyBuddy.controller.UserController;
import com.api.payMyBuddy.exceptions.NotFoundInDatabaseException;
import com.api.payMyBuddy.model.entity.UserEntity;
import com.api.payMyBuddy.model.repository.UserEntityRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AccountService {

    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ResponseEntity<Object> addMoney(String email, int amount) throws RuntimeException {
        if (amount<0) {
            logger.error("Please enter a positive amount");
            return new ResponseEntity<>("Please enter a positive amount", HttpStatus.BAD_REQUEST);
        } else {
            Optional<UserEntity> userEntityOptional = userEntityRepository.findByEmail(email);
            if (userEntityOptional.isEmpty()) {
                logger.error(String.format("User %s not found", email));
                throw new NotFoundInDatabaseException("User not found");
            } else {
                UserEntity userEntity = userEntityOptional.get();
                userEntity.updateBalance(amount);
                userEntityRepository.saveAndFlush(userEntity);
                logger.info(String.format("New balance : %s", userEntity.getBalance()));
                return ResponseEntity.ok("Account updated");
            }
        }
    }
}
