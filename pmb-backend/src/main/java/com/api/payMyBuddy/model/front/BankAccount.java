package com.api.payMyBuddy.model.front;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankAccount {

    @NotNull
    @NotEmpty
    private String bank;

    @NotNull
    @NotEmpty
    private String iban;

    @NotNull
    @NotEmpty
    private String bic;
}
