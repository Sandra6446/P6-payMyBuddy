package com.api.payMyBuddy.service.mapper;

import com.api.payMyBuddy.model.entity.TransferEntity;
import com.api.payMyBuddy.model.entity.UserEntity;
import com.api.payMyBuddy.model.front.Transaction;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MapperTransfer {

    public List<Transaction> getTransactions(UserEntity userEntity) {
        List<Transaction> transactions = new ArrayList<>();
        for (TransferEntity transferEntity : userEntity.getTransferEntities()) {
            transactions.add(new Transaction(transferEntity));
        }
        return transactions;
    }

}
