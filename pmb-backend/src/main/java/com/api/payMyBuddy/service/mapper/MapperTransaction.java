package com.api.payMyBuddy.service.mapper;

import com.api.payMyBuddy.model.entity.TransactionEntity;
import com.api.payMyBuddy.model.entity.UserEntity;
import com.api.payMyBuddy.model.front.Transaction;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Organizes user connection
 */
@Component
public class MapperTransaction {

    /**
     * Gets all transaction made by a user (debits), organized by date
     *
     * @param userEntity : The user entity containing the transaction list to be sorted
     * @return A transaction list, sorted by date
     */
    public List<Transaction> getDebits(UserEntity userEntity) {
        List<Transaction> debits = new ArrayList<>();
        for (TransactionEntity transactionEntity : userEntity.getDebits()) {
            Transaction transaction = new Transaction(transactionEntity, true);
            debits.add(transaction);
        }
        debits.sort(Comparator.comparing(Transaction::getDate).reversed());
        return debits;
    }

    /**
     * Gets all transaction of a user (debits and credits), organized by date
     *
     * @param userEntity : The user entity containing the transaction list to be sorted
     * @return A transaction list, sorted by date
     */
    public List<Transaction> getAllTransactions(UserEntity userEntity) {
        List<Transaction> transactions = new ArrayList<>();
        for (TransactionEntity transactionEntity : userEntity.getCredits()) {
            transactions.add(new Transaction(transactionEntity, false));
        }
        for (TransactionEntity transactionEntity : userEntity.getDebits()) {
            transactions.add(new Transaction(transactionEntity, true));
        }
        transactions.sort(Comparator.comparing(Transaction::getDate).reversed());
        return transactions;
    }

}
