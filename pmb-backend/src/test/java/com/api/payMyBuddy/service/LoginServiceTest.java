package com.api.payMyBuddy.service;

import com.api.payMyBuddy.model.entity.UserEntity;
import com.api.payMyBuddy.model.front.Login;
import com.api.payMyBuddy.model.repository.UserEntityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
class LoginServiceTest {

    @MockBean
    private UserEntityRepository userEntityRepository;

    private LoginService loginService;

    private static UserEntity userEntity;

    @BeforeAll
    private static void setUp() {
        // UserEntity
        userEntity = new UserEntity("user@email.com", "Prenom", "Nom", "pwd",  20.5, "bank", "iban", "bic", null, null, null);
    }

    @BeforeEach
    private void setUpPerTest() {
        loginService = new LoginService(userEntityRepository);
    }

    @Test
    void checkLogin() {

        // Login
        Login login = new Login("user@email.com","pwd",null);
        String response = "User " + userEntity.getEmail() + " authorized.";

        // Test to check an authorized user
        Mockito.when(userEntityRepository.findById(ArgumentMatchers.anyString())).thenReturn(Optional.of(userEntity));
        Assertions.assertEquals(ResponseEntity.ok(response), loginService.checkLogin(login));

        // Test to check a user with bad email
        response = "Email or password incorrect";
        Mockito.when(userEntityRepository.findById(ArgumentMatchers.anyString())).thenReturn(Optional.empty());
        Assertions.assertEquals(new ResponseEntity<>(response,HttpStatus.UNAUTHORIZED), loginService.checkLogin(login));

        // Test to check a user with bad password
        login.setPassword("badPassword");
        Mockito.when(userEntityRepository.findById(ArgumentMatchers.anyString())).thenReturn(Optional.of(userEntity));
        Assertions.assertEquals(new ResponseEntity<>(response,HttpStatus.UNAUTHORIZED), loginService.checkLogin(login));

    }
}