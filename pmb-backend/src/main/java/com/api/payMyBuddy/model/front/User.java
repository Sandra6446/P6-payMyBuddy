package com.api.payMyBuddy.model.front;

import com.api.payMyBuddy.model.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private double balance;

    @NotNull
    @Valid
    private BankAccount bankAccount;

    public User(UserEntity userEntity) {
        this.setEmail(userEntity.getEmail());
        this.setPassword(userEntity.getPassword());
        this.setConfirmPassword("");
        this.setFirstName(userEntity.getFirstName());
        this.setLastName(userEntity.getLastName());
        this.setBalance(userEntity.getBalance());
        this.setBankAccount(new BankAccount(userEntity.getBank(), userEntity.getIban(), userEntity.getBic()));
    }

    public User(@NotNull @NotEmpty String email, @NotNull @NotEmpty String password, String confirmPassword, String firstName, String lastName, double balance, BankAccount bankAccount) {
        super(email, password, confirmPassword);
        this.firstName = firstName;
        this.lastName = lastName;
        this.balance = balance;
        this.bankAccount = bankAccount;
    }

    @SneakyThrows
    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }
}
