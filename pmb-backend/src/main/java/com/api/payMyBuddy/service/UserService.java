package com.api.payMyBuddy.service;

import com.api.payMyBuddy.controller.UserController;
import com.api.payMyBuddy.model.entity.UserEntity;
import com.api.payMyBuddy.model.front.User;
import com.api.payMyBuddy.model.repository.UserEntityRepository;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private UserEntityRepository userEntityRepository;

    public ResponseEntity<Object> createUser(User user) {
        if (user.getConfirmPassword().isEmpty()) {
            logger.error("The two passwords are required");
            return new ResponseEntity<>("The two passwords are required", HttpStatus.BAD_REQUEST);
        } else if (!user.getPassword().equals(user.getConfirmPassword())) {
            logger.error("The two passwords are different");
            return new ResponseEntity<>("The two passwords are different", HttpStatus.BAD_REQUEST);
        } else {
            Optional<UserEntity> userEntityOptional = userEntityRepository.findByEmail(user.getEmail());
            if (!userEntityOptional.isEmpty()) {
                logger.error("User already In Database");
                return new ResponseEntity<>("User already registered", HttpStatus.CONFLICT);
            } else {
                UserEntity userEntity = new UserEntity(user);
                userEntityRepository.saveAndFlush(userEntity);
                logger.info("User " + user.getEmail() + " created");
                return new ResponseEntity<>("User " + user.getEmail() + " created", HttpStatus.CREATED);
            }
        }
    }

    public ResponseEntity<Object> readUserByEmail(String user_email) {
        Optional<UserEntity> userEntityOptional = userEntityRepository.findByEmail(user_email);
        if (userEntityOptional.isEmpty()) {
            logger.error("User not found");
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        } else {
            User user = new User(userEntityOptional.get());
            return ResponseEntity.ok(user);
        }
    }

    public ResponseEntity<Object> getBalance(String user_email) {
        Optional<UserEntity> userEntityOptional = userEntityRepository.findByEmail(user_email);
        if (userEntityOptional.isEmpty()) {
            logger.error("User not found");
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        } else {
            User user = new User(userEntityOptional.get());
            String balance = "{\"balance\": " + user.getBalance() + "}";
            return ResponseEntity.ok(balance);
        }
    }

    // TODO Vérifier création User avec changement de mail
    public ResponseEntity<Object> updateUser(String email, User user) {
        if (!user.getPassword().isEmpty() && user.getConfirmPassword().isEmpty()) {
            logger.error("Confirm password is required");
            return new ResponseEntity<>("Confirm password is required", HttpStatus.BAD_REQUEST);
        } else if (!user.getPassword().isEmpty() && !user.getPassword().equals(user.getConfirmPassword())) {
            logger.error("The two passwords are different");
            return new ResponseEntity<>("The two passwords are different", HttpStatus.BAD_REQUEST);
        } else {
            Optional<UserEntity> userEntityOptional = userEntityRepository.findByEmail(email);
            if (userEntityOptional.isEmpty()) {
                logger.error("User not found");
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            } else {
                UserEntity userEntity = userEntityOptional.get();
                userEntity.update(user);
                userEntityRepository.saveAndFlush(userEntity);
                logger.info("User " + user.getEmail() + " updated");
                return ResponseEntity.ok("User " + user.getEmail() + " updated");
            }
        }
    }
}

