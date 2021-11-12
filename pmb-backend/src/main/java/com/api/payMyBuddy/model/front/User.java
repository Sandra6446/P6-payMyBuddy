package com.api.payMyBuddy.model.front;

import com.api.payMyBuddy.model.entity.NetworkEntity;
import com.api.payMyBuddy.model.entity.TransferEntity;
import com.api.payMyBuddy.model.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonFilter("userFilter")
public class User extends Login {

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    private float balance;

    @NotNull
    @Valid
    private BankAccount bankAccount;

    @Valid
    private List<Connection> connections;

    @Valid
    private List<Transaction> transactions;

    public User(UserEntity userEntity) {
        this.setEmail(userEntity.getEmail());
        this.setPassword(userEntity.getPassword());
        this.setConfirmPassword("");
        this.setFirstName(userEntity.getFirstName());
        this.setLastName(userEntity.getLastName());
        this.setBalance(userEntity.getBalance());
        this.setBankAccount(new BankAccount(userEntity.getBank(), userEntity.getIban(), userEntity.getBic()));
        List<Connection> connections = new ArrayList<>();
            for (NetworkEntity connectionEntity : userEntity.getNetworkEntities()) {
                connections.add(new Connection(connectionEntity.getNetworkPrimaryKey().getConnection()));
            }
        this.setConnections(connections);
    }

}
