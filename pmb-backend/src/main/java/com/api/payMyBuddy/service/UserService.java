package com.api.payMyBuddy.service;

import com.api.payMyBuddy.controller.UserController;
import com.api.payMyBuddy.exceptions.AlreadyInDatabaseException;
import com.api.payMyBuddy.exceptions.NotFoundInDatabaseException;
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
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ResponseEntity<Object> createUser(User user) throws RuntimeException {
        if (user.getConfirmPassword().isEmpty()) {
            logger.error("The two passwords are required");
            return new ResponseEntity<>("The two passwords are required", HttpStatus.BAD_REQUEST);
        } else if (!user.getPassword().equals(user.getConfirmPassword())) {
            logger.error("The two passwords are different");
            return new ResponseEntity<>("The two passwords are different", HttpStatus.BAD_REQUEST);
        } else {
            Optional<UserEntity> userEntityOptional = userEntityRepository.findByEmail(user.getEmail());
            if (userEntityOptional.isPresent()) {
                logger.error(String.format("User %s already in database", user.getEmail()));
                throw new AlreadyInDatabaseException("User already in database");
            } else {
                UserEntity userEntity = new UserEntity(user);
                userEntityRepository.saveAndFlush(userEntity);
                logger.info(String.format("User created : %s", user));
                return new ResponseEntity<>("User created", HttpStatus.CREATED);
            }
        }
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public ResponseEntity<Object> getUser(String email) throws RuntimeException {
        Optional<UserEntity> userEntityOptional = userEntityRepository.findByEmail(email);
        if (userEntityOptional.isEmpty()) {
            logger.error(String.format("User %s not found", email));
            throw new NotFoundInDatabaseException("User not found");
        } else {
            User user = new User(userEntityOptional.get());
            logger.info(user.toString());
            return ResponseEntity.ok(user);
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ResponseEntity<Object> updateUser(String email, User user) throws RuntimeException {
        if (!user.getPassword().isEmpty() && user.getConfirmPassword().isEmpty()) {
            logger.error("Confirm password is required");
            return new ResponseEntity<>("Confirm password is required", HttpStatus.BAD_REQUEST);
        } else if (!user.getPassword().isEmpty() && !user.getPassword().equals(user.getConfirmPassword())) {
            logger.error("The two passwords are different");
            return new ResponseEntity<>("The two passwords are different", HttpStatus.BAD_REQUEST);
        } else {
            Optional<UserEntity> newUserOptional = userEntityRepository.findByEmail(user.getEmail());
            if (newUserOptional.isPresent()) {
                logger.error(String.format("Email %s already in database", user.getEmail()));
                throw new AlreadyInDatabaseException("New email not valid");
            } else {
                Optional<UserEntity> userEntityOptional = userEntityRepository.findByEmail(email);
                if (userEntityOptional.isEmpty()) {
                    logger.error(String.format("User %s not found", email));
                    throw new NotFoundInDatabaseException("User not found");
                } else {
                    UserEntity userEntity = userEntityOptional.get();
                    userEntity.update(user);
                    userEntityRepository.saveAndFlush(userEntity);
                    logger.info(String.format("User updated : %s", user));
                    return ResponseEntity.ok("Profile updated");
                }
            }
        }
    }
}

