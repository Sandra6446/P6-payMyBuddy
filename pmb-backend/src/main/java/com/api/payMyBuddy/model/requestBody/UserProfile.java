package com.api.payMyBuddy.model.requestBody;

import com.api.payMyBuddy.model.front.BankAccount;
import com.api.payMyBuddy.model.front.Login;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class UserProfile extends Login {

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    @Valid
    private BankAccount bankAccount;

    public UserProfile(@NotNull String email, @NotNull String password, String confirmPassword, String firstName, String lastName, BankAccount bankAccount) {
        super(email, password, confirmPassword);
        this.firstName = firstName;
        this.lastName = lastName;
        this.bankAccount = bankAccount;
    }
}
