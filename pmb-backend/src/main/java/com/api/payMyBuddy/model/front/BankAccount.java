package com.api.payMyBuddy.model.front;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankAccount {

    @NotNull
    private String bank;

    @NotNull
    private String iban;

    @NotNull
    private String bic;
}
