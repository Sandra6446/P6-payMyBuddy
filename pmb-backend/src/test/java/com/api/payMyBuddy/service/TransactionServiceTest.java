package com.api.payMyBuddy.service;

import com.api.payMyBuddy.exceptions.NotEnoughMoneyException;
import com.api.payMyBuddy.exceptions.NotFoundInDatabaseException;
import com.api.payMyBuddy.model.entity.TransactionEntity;
import com.api.payMyBuddy.model.entity.TransactionPrimaryKey;
import com.api.payMyBuddy.model.entity.UserEntity;
import com.api.payMyBuddy.model.front.BankAccount;
import com.api.payMyBuddy.model.front.Transaction;
import com.api.payMyBuddy.model.front.User;
import com.api.payMyBuddy.model.repository.TransactionEntityRepository;
import com.api.payMyBuddy.model.repository.UserEntityRepository;
import com.api.payMyBuddy.service.mapper.MapperTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class TransactionServiceTest {

    @MockBean
    private UserEntityRepository userEntityRepository;

    @MockBean
    private TransactionEntityRepository transactionEntityRepository;

    @MockBean
    private MapperTransaction mapperTransaction;

    private TransactionService transactionService;

    private static String userEmail;
    private static User user;
    private UserEntity userEntity;
    private static UserEntity userEntityConnection;
    private static Transaction debit;
    private static Transaction credit;

    @BeforeAll
    private static void setUp() {
        // User
        userEmail = "user@email.com";
        BankAccount bankAccount = new BankAccount("ma banque", "iban", "bic");
        user = new User(userEmail,"pwd",null,"Monsieur","Test",50,bankAccount);

        // UserEntityConnection
        userEntityConnection = new UserEntity(user);
        userEntityConnection.setEmail("connectionEmail@email.com");
    }

    @BeforeEach
    private void setUpPerTest() {
        transactionService = new TransactionService(userEntityRepository, transactionEntityRepository,mapperTransaction);

        // UserEntity
        userEntity = new UserEntity(user);

        // ConnectionEntity
        TransactionPrimaryKey transactionPrimaryKey = new TransactionPrimaryKey();
        transactionPrimaryKey.setUserEntity(userEntity);
        transactionPrimaryKey.setContactEntity(userEntityConnection);
        transactionPrimaryKey.setDate(LocalDateTime.now());
        TransactionEntity transactionEntity = new TransactionEntity(transactionPrimaryKey, 20, "Ma transaction de test", null, null);

        // Connection
        debit = new Transaction(transactionEntity,true);
        credit = new Transaction(transactionEntity,false);
    }

    @Test
    void getMyTransactions() {
        // Test to get transactions made by an invalid user
        Mockito.when(userEntityRepository.findByEmail(userEmail)).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundInDatabaseException.class, () -> transactionService.getMyTransactions(userEmail));

        // Test to get transactions made by a user
        Mockito.when(userEntityRepository.findByEmail(userEmail)).thenReturn(Optional.of(userEntity));
        Mockito.when(mapperTransaction.getDebits(userEntity)).thenReturn(List.of(debit));
        Assertions.assertEquals(ResponseEntity.ok(List.of(debit)), transactionService.getMyTransactions(userEmail));
    }

    @Test
    void getAllTransactions() {
        // Test to get transactions of an invalid user
        Mockito.when(userEntityRepository.findByEmail(userEmail)).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundInDatabaseException.class, () -> transactionService.getAllTransactions(userEmail));

        // Test to get transactions of a user
        Mockito.when(userEntityRepository.findByEmail(userEmail)).thenReturn(Optional.of(userEntity));
        Mockito.when(mapperTransaction.getAllTransactions(userEntity)).thenReturn(List.of(debit,credit));
        Assertions.assertEquals(ResponseEntity.ok(List.of(debit,credit)), transactionService.getAllTransactions(userEmail));
    }

    @Test
    void createTransaction() {
        userEntity.setBalance(20);
        Mockito.when(userEntityRepository.findByEmail(userEmail)).thenReturn(Optional.of(userEntity));
        Mockito.when(userEntityRepository.findByEmail("connectionEmail@email.com")).thenReturn(Optional.of(userEntityConnection));
        Assertions.assertEquals(new ResponseEntity<>("Transaction added", HttpStatus.CREATED), transactionService.createTransaction(debit));

        // Test to add a transaction to an invalid user
        Mockito.when(userEntityRepository.findByEmail(userEmail)).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundInDatabaseException.class, () -> transactionService.createTransaction(debit));

        // Test to add a transaction with an invalid connection
        Mockito.when(userEntityRepository.findByEmail(userEmail)).thenReturn(Optional.of(userEntity));
        Mockito.when(userEntityRepository.findByEmail("connectionEmail@email.com")).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundInDatabaseException.class, () -> transactionService.createTransaction(debit));


        // Test to add a transaction with a too big amount
        userEntity.setBalance(10);
        Mockito.when(userEntityRepository.findByEmail(userEmail)).thenReturn(Optional.of(userEntity));
        Mockito.when(userEntityRepository.findByEmail("connectionEmail@email.com")).thenReturn(Optional.of(userEntityConnection));
        Assertions.assertThrows(NotEnoughMoneyException.class, () -> transactionService.createTransaction(debit));
    }
}