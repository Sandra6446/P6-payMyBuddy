package com.api.payMyBuddy.service;

import com.api.payMyBuddy.controller.UserController;
import com.api.payMyBuddy.exceptions.UnauthorizedException;
import com.api.payMyBuddy.model.entity.UserEntity;
import com.api.payMyBuddy.model.front.Login;
import com.api.payMyBuddy.model.repository.UserEntityRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class LoginService {

    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private final UserEntityRepository userEntityRepository;

    public LoginService(UserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ResponseEntity<String> checkLogin(Login login) {
        boolean authorized = false;
        Optional<UserEntity> userEntityOptional = userEntityRepository.findByEmail(login.getEmail());
        if (userEntityOptional.isPresent()) {
            authorized = (userEntityOptional.get().getPassword().equals(login.getPassword()));
        }
        if (authorized) {
            logger.info(String.format("User %s authorized", login.getEmail()));
            return ResponseEntity.ok("User authorized");
        } else {
            logger.error("Email or password incorrect");
            throw new UnauthorizedException("Email or password incorrect");
        }
    }
}
