package com.api.payMyBuddy.service.mapper;

import com.api.payMyBuddy.model.entity.TransactionEntity;
import com.api.payMyBuddy.model.entity.TransactionPrimaryKey;
import com.api.payMyBuddy.model.entity.UserEntity;
import com.api.payMyBuddy.model.front.Transaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class MapperTransactionTest {

    private static UserEntity userEntity;
    private final MapperTransaction mapperTransaction = new MapperTransaction();

    @BeforeAll
    private static void setUp() {
        userEntity = new UserEntity(1, "user@email.com", "User", "Test", "pwd", 20, "Bank", "Iban", "Bic", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        UserEntity userEntityConnection = new UserEntity(2, "connection@email.com", "Contact", "Test", "pwd", 50, "Bank", "Iban", "Bic", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        TransactionEntity debit1 = new TransactionEntity(new TransactionPrimaryKey(userEntity, userEntityConnection, LocalDateTime.now()), 10, "My first debit", null, null);
        TransactionEntity debit2 = new TransactionEntity(new TransactionPrimaryKey(userEntity, userEntityConnection, LocalDateTime.now()), 10, "My second debit", null, null);
        TransactionEntity credit = new TransactionEntity(new TransactionPrimaryKey(userEntityConnection, userEntity, LocalDateTime.now()), 10, "My first credit", null, null);
        List<TransactionEntity> debits = new ArrayList<>(List.of(debit1, debit2));
        userEntity.setDebits(debits);
        List<TransactionEntity> credits = new ArrayList<>(List.of(credit));
        userEntity.setCredits(credits);
    }

    @Test
    void getDebits() {
        Transaction transaction1 = new Transaction(userEntity.getDebits().get(0), true);
        Transaction transaction2 = new Transaction(userEntity.getDebits().get(1), true);
        List<Transaction> transactions = new ArrayList<>(List.of(transaction2,transaction1));
        Assertions.assertIterableEquals(transactions, mapperTransaction.getDebits(userEntity));
    }

    @Test
    void getAllTransactions() {
        Transaction transaction1 = new Transaction(userEntity.getDebits().get(0), true);
        Transaction transaction2 = new Transaction(userEntity.getDebits().get(1), true);
        Transaction transaction3 = new Transaction(userEntity.getCredits().get(0), false);
        List<Transaction> transactions = new ArrayList<>(List.of(transaction3,transaction2,transaction1));
        Assertions.assertIterableEquals(transactions, mapperTransaction.getAllTransactions(userEntity));
    }
}