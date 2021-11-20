package com.api.payMyBuddy.model.front;

import com.api.payMyBuddy.model.entity.ConnectionEntity;
import com.api.payMyBuddy.model.entity.TransactionEntity;
import com.api.payMyBuddy.model.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonFilter("userFilter")
public class User extends Login {

    @NotNull
    @NotEmpty
    private String firstName;

    @NotNull
    @NotEmpty
    private String lastName;

    @NotNull
    private float balance;

    @NotNull
    @Valid
    private BankAccount bankAccount;

    @Valid
    private List<Connection> connections;

    @Valid
    private List<Transaction> debits;

    @Valid
    private List<Transaction> credits;

    public User(UserEntity userEntity) {
        this.setEmail(userEntity.getEmail());
        this.setPassword(userEntity.getPassword());
        this.setConfirmPassword("");
        this.setFirstName(userEntity.getFirstName());
        this.setLastName(userEntity.getLastName());
        this.setBalance(userEntity.getBalance());
        this.setBankAccount(new BankAccount(userEntity.getBank(), userEntity.getIban(), userEntity.getBic()));

        List <Transaction> debits = new ArrayList<>();
        if (!userEntity.getDebits().isEmpty()) {
            for (TransactionEntity debit : userEntity.getDebits()) {
                debits.add(new Transaction(debit,true));
            }
        }
        debits.sort(Comparator.comparing(Transaction::getDate).reversed());
        this.setDebits(debits);

        List <Transaction> credits = new ArrayList<>();
        if (!userEntity.getCredits().isEmpty()) {
            for (TransactionEntity credit : userEntity.getCredits()) {
                credits.add(new Transaction(credit,false));
            }
        }
        credits.sort(Comparator.comparing(Transaction::getDate).reversed());
        this.setCredits(credits);

        List <Connection> connections = new ArrayList<>();
        if (!userEntity.getConnectionEntities().isEmpty()) {
            for (ConnectionEntity connectionEntity : userEntity.getConnectionEntities()) {
                connections.add(new Connection(connectionEntity));
            }
        }
        this.setConnections(connections);
    }

}
