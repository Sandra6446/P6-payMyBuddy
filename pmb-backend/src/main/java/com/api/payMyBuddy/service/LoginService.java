package com.api.payMyBuddy.service;

import com.api.payMyBuddy.controller.UserController;
import com.api.payMyBuddy.model.entity.UserEntity;
import com.api.payMyBuddy.model.front.Login;
import com.api.payMyBuddy.model.repository.UserEntityRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private final UserEntityRepository userEntityRepository;

    public LoginService(UserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }

    public ResponseEntity<String> checkLogin(Login login) {
        boolean authorized = false;
        Optional<UserEntity> userEntityOptional = userEntityRepository.findByEmail(login.getEmail());

        if (userEntityOptional.isPresent()) {
            authorized = (userEntityOptional.get().getPassword().equals(login.getPassword()));
        }
        if (authorized) {
            logger.info("User " + login.getEmail() + " found in database.");
            return ResponseEntity.ok("User " + login.getEmail() + " authorized.");
        } else {
            logger.error("User Not Found In Database");
            return new ResponseEntity<>("Email or password incorrect", HttpStatus.UNAUTHORIZED);
        }
    }
}
