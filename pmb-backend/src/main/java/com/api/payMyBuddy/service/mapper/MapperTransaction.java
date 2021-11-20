package com.api.payMyBuddy.service.mapper;

import com.api.payMyBuddy.model.front.Transaction;
import com.api.payMyBuddy.model.front.User;
import com.api.payMyBuddy.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class MapperTransaction {

    @Autowired
    TransactionService transactionService;

    public List<Transaction> getAllTransactions(User user) {
        List<Transaction> credits = user.getCredits();
        List<Transaction> debits = user.getDebits();
        List<Transaction> transactions = new ArrayList<>();
        transactions.addAll(credits);
        transactions.addAll(debits);
        transactions.sort(Comparator.comparing(Transaction::getDate).reversed());
        return transactions;
    }

}
