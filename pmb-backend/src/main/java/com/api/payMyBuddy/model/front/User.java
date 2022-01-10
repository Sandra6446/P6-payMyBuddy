package com.api.payMyBuddy.model.front;

import com.api.payMyBuddy.model.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.StringJoiner;

/**
 * Represents a user
 *
 * @see Login
 * @see UserEntity
 */
@Data
@NoArgsConstructor
public class User extends Login {

    /**
     * The user first name
     */
    @NotNull
    @NotEmpty
    private String firstName;

    /**
     * The user last name
     */
    @NotNull
    @NotEmpty
    private String lastName;

    /**
     * The user balance in the application
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private double balance;

    /**
     * The user bank account
     */
    @NotNull
    @Valid
    private BankAccount bankAccount;

    public User(UserEntity userEntity) {
        this.setEmail(userEntity.getEmail());
        this.setPassword(userEntity.getPassword());
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

    @Override
    public String toString() {
        return new StringJoiner(", ", "" + "{", "}")
                .add("\"email\":\"" + email + "\"")
                .add("\"password\":\"" + password + "\"")
                .add("\"confirmPassword\":\"" + confirmPassword + "\"")
                .add("\"firstName\":\"" + firstName + "\"")
                .add("\"lastName\":\"" + lastName + "\"")
                .add("\"balance\":" + balance)
                .add("\"bankAccount\":" + bankAccount)
                .toString();
    }
}
