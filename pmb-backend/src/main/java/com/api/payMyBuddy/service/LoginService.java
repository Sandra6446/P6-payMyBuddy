package com.api.payMyBuddy.service;

import com.api.payMyBuddy.controller.UserController;
import com.api.payMyBuddy.model.entity.UserEntity;
import com.api.payMyBuddy.model.front.Login;
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
public class LoginService {

    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private UserEntityRepository userEntityRepository;

    public ResponseEntity checkLogin(Login login) {
        Optional<UserEntity> userEntityOptional = userEntityRepository.findById(login.getEmail());
        if (userEntityOptional.isPresent() && userEntityOptional.get().getPassword().equals(login.getPassword())) {
            logger.info("User " + login.getEmail() + " found in database.");
            return ResponseEntity.ok("User " + login.getEmail() + " authorized.");
        } else {
            logger.error("User Not Found In Database");
            return new ResponseEntity<>("Email or password incorrect", HttpStatus.UNAUTHORIZED);
        }
    }
}
