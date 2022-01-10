package com.api.payMyBuddy.service;

import com.api.payMyBuddy.controller.UserController;
import com.api.payMyBuddy.exceptions.NotEnoughMoneyException;
import com.api.payMyBuddy.exceptions.NotFoundInDatabaseException;
import com.api.payMyBuddy.model.entity.TransactionEntity;
import com.api.payMyBuddy.model.entity.UserEntity;
import com.api.payMyBuddy.model.front.Transaction;
import com.api.payMyBuddy.model.repository.TransactionEntityRepository;
import com.api.payMyBuddy.model.repository.UserEntityRepository;
import com.api.payMyBuddy.service.mapper.MapperTransaction;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Manages user transactions
 */
@Service
@AllArgsConstructor
public class TransactionService {
    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Autowired
    private TransactionEntityRepository transactionEntityRepository;

    @Autowired
    private MapperTransaction mapperTransaction;

    /**
     * Gets transactions made by a user
     *
     * @param email : The current user email
     * @return Status OK, with the transaction list if the operation succeeds, otherwise the reason of the failure
     */
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public ResponseEntity<Object> getMyTransactions(String email) throws RuntimeException {
        Optional<UserEntity> userEntityOptional = userEntityRepository.findByEmail(email);
        if (userEntityOptional.isEmpty()) {
            logger.error(String.format("User %s not found", email));
            throw new NotFoundInDatabaseException("User not found");
        } else {
            List<Transaction> debits = mapperTransaction.getDebits(userEntityOptional.get());
            logger.info("Debits found");
            return ResponseEntity.ok(debits);
        }
    }

    /**
     * Gets all the transactions of a user
     *
     * @param email : The current user email
     * @return Status OK, with the transaction list if the operation succeeds, otherwise the reason of the failure
     */
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public ResponseEntity<Object> getAllTransactions(String email) throws RuntimeException {
        Optional<UserEntity> userEntityOptional = userEntityRepository.findByEmail(email);
        if (userEntityOptional.isEmpty()) {
            logger.error(String.format("User %s not found", email));
            throw new NotFoundInDatabaseException("User not found");
        } else {
            List<Transaction> transactions = mapperTransaction.getAllTransactions(userEntityOptional.get());
            logger.info("Transactions found");
            return ResponseEntity.ok(transactions);
        }
    }

    /**
     * Adds a transaction
     *
     * @param transaction : the transaction to be added
     * @return Status CREATED, "Transaction added" if the operation succeeds, otherwise the reason of the failure
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ResponseEntity<String> createTransaction(Transaction transaction) throws RuntimeException {
        Optional<UserEntity> userEntityOptional = userEntityRepository.findByEmail(transaction.getUserEmail());
        if (userEntityOptional.isEmpty()) {
            logger.error(String.format("User %s not found", transaction.getUserEmail()));
            throw new NotFoundInDatabaseException("User not found");
        } else {
            UserEntity userEntity = userEntityOptional.get();
            Optional<UserEntity> userEntityConnectionOptional = userEntityRepository.findByEmail(transaction.getConnectionEmail());
            if (userEntityConnectionOptional.isEmpty()) {
                logger.error(String.format("Connection %s not found", transaction.getConnectionEmail()));
                throw new NotFoundInDatabaseException("Connection not found");
            } else {
                UserEntity userEntityConnection = userEntityConnectionOptional.get();
                TransactionEntity transactionEntity = new TransactionEntity(userEntity, userEntityConnection, transaction);
                transactionEntityRepository.saveAndFlush(transactionEntity);
                userEntity.updateBalance(-transactionEntity.getAmount());
                if (userEntity.getBalance() >= 0) {
                    userEntityRepository.saveAndFlush(userEntity);
                } else {
                    logger.error(String.format("Balance negative : %s ", userEntity.getBalance()));
                    throw new NotEnoughMoneyException("Please recharge money");
                }
                userEntityConnection.updateBalance(transactionEntity.getAmount());
                userEntityRepository.saveAndFlush(userEntityConnection);
                logger.info(String.format("User %s : Transaction %s registered", transaction.getUserEmail(), transaction));
                return new ResponseEntity<>("Transaction added", HttpStatus.CREATED);
            }
        }
    }
}
