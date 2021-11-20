package com.api.payMyBuddy.service;

import com.api.payMyBuddy.controller.UserController;
import com.api.payMyBuddy.model.entity.UserEntity;
import com.api.payMyBuddy.model.front.User;
import com.api.payMyBuddy.model.repository.UserEntityRepository;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Data
@Service
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
            Optional<UserEntity> userEntityOptional = userEntityRepository.findById(user.getEmail());
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
        Optional<UserEntity> userEntityOptional = userEntityRepository.findById(user_email);
        if (userEntityOptional.isEmpty()) {
            logger.error("User not found");
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        } else {
            User user = new User(userEntityOptional.get());
            return ResponseEntity.ok(user);
        }
    }

    public ResponseEntity<Object> getBalance(String user_email) {
        User user = (User) readUserByEmail(user_email).getBody();
        if (Objects.isNull(user.getEmail())) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        } else {
            FilterProvider filterProvider = new SimpleFilterProvider()
                    .addFilter("userFilter", SimpleBeanPropertyFilter.filterOutAllExcept("balance"));
            MappingJacksonValue connections = new MappingJacksonValue(user);
            connections.setFilters(filterProvider);
            return ResponseEntity.ok(connections);
        }
    }

    public ResponseEntity<Object> updateUser(User user) {
        if (!user.getPassword().isEmpty() && user.getConfirmPassword().isEmpty()) {
            logger.error("Confirm password is required");
            return new ResponseEntity<>("Confirm password is required", HttpStatus.BAD_REQUEST);
        } else if (!user.getPassword().isEmpty() && !user.getPassword().equals(user.getConfirmPassword())) {
            logger.error("The two passwords are different");
            return new ResponseEntity<>("The two passwords are different", HttpStatus.BAD_REQUEST);
        } else {
            Optional<UserEntity> userEntityOptional = userEntityRepository.findById(user.getEmail());
            if (userEntityOptional.isEmpty()) {
                logger.error("User not found");
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            } else {
                if (user.getPassword().isEmpty()) {
                    user.setPassword(userEntityOptional.get().getPassword());
                }
                UserEntity userEntity = new UserEntity(user);
                userEntityRepository.saveAndFlush(userEntity);
                logger.info("User " + user.getEmail() + " updated");
                return ResponseEntity.ok("User " + user.getEmail() + " updated");
            }
        }
    }
}

