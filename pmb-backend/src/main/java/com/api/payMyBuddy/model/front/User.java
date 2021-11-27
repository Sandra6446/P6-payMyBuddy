package com.api.payMyBuddy.model.front;

import com.api.payMyBuddy.model.entity.ConnectionEntity;
import com.api.payMyBuddy.model.entity.TransactionEntity;
import com.api.payMyBuddy.model.entity.UserEntity;
import com.api.payMyBuddy.model.requestBody.UserProfile;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class User extends Login {

    @NotNull
    @NotEmpty
    private String firstName;

    @NotNull
    @NotEmpty
    private String lastName;

    private double balance;

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

    public User(@NotNull @NotEmpty String email, @NotNull @NotEmpty String password, String confirmPassword, String firstName, String lastName, double balance, BankAccount bankAccount, List<Connection> connections, List<Transaction> debits, List<Transaction> credits) {
        super(email, password, confirmPassword);
        this.firstName = firstName;
        this.lastName = lastName;
        this.balance = balance;
        this.bankAccount = bankAccount;
        this.connections = connections;
        this.debits = debits;
        this.credits = credits;
    }

    public User(UserProfile userProfile) {
        super (userProfile.getEmail(),userProfile.getPassword(),null);
        this.firstName = userProfile.getFirstName();
        this.lastName = userProfile.getLastName();
        BankAccount bankAccount = new BankAccount(userProfile.getBankAccount().getBank(),userProfile.getBankAccount().getIban(),userProfile.getBankAccount().getBic());
        this.bankAccount = bankAccount;
    }

    @SneakyThrows
    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }
}
