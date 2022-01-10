package com.api.payMyBuddy.service;

import com.api.payMyBuddy.exceptions.NotFoundInDatabaseException;
import com.api.payMyBuddy.model.entity.UserEntity;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Optional;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class AccountServiceTest {

    @MockBean
    private UserEntityRepository userEntityRepository;

    private static UserEntity userEntity;

    private AccountService accountService;

    @BeforeAll
    private static void setUp() {
        userEntity = new UserEntity(1, "user@email.com", "User", "Test", "pwd", 20, "Bank", "Iban", "Bic", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    @BeforeEach
    private void setUpPerTest() {
        accountService = new AccountService(userEntityRepository);
    }

    @Test
    void addMoney() {

        Mockito.when(userEntityRepository.findByEmail(ArgumentMatchers.anyString())).thenReturn(Optional.of(userEntity));
        Mockito.when(userEntityRepository.saveAndFlush(ArgumentMatchers.any(UserEntity.class))).thenReturn(userEntity);

        // Test to add a positive amount to a registered user
        Assertions.assertEquals(ResponseEntity.ok("Account updated"), accountService.addMoney(userEntity.getEmail(), 20));

        // Test to add null amount to a registered user
        Assertions.assertEquals(new ResponseEntity<>("Please enter a positive amount", HttpStatus.BAD_REQUEST), accountService.addMoney(userEntity.getEmail(), 0));

        // Test to add a negative amount to a registered user
        Assertions.assertEquals(new ResponseEntity<>("Please enter a positive amount", HttpStatus.BAD_REQUEST), accountService.addMoney(userEntity.getEmail(), -20));

        // Test to add a positive amount to an unregistered user
        Mockito.when(userEntityRepository.findByEmail(ArgumentMatchers.anyString())).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundInDatabaseException.class, () -> accountService.addMoney(userEntity.getEmail(), 20));
    }
}