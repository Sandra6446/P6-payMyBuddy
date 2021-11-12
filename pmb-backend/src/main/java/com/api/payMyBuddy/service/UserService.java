package com.api.payMyBuddy.service;

import com.api.payMyBuddy.controller.UserController;
import com.api.payMyBuddy.model.entity.UserEntity;
import com.api.payMyBuddy.model.front.User;
import com.api.payMyBuddy.model.repository.UserEntityRepository;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
            if (userEntityOptional.isPresent()) {
                logger.error("User already In Database");
                return new ResponseEntity<>("User already registered", HttpStatus.CONFLICT);
            } else {
                UserEntity userEntity = new UserEntity(user);
                userEntityRepository.saveAndFlush(userEntity);
                logger.info("User " + user.getEmail() + " created" );
                return new ResponseEntity<>("User " + user.getEmail() + " created", HttpStatus.CREATED);
            }
        }
    }

    public User readUserByEmail(String email) {
        UserEntity userEntity = userEntityRepository.findById(email).orElse(new UserEntity());
        return new User(userEntity);
    }
}
