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
public class UserEntityService {

    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private UserEntityRepository userEntityRepository;

    public UserEntity readUserEntityByEmail(String email) {
        return userEntityRepository.findById(email).orElse(new UserEntity());
    }
}
