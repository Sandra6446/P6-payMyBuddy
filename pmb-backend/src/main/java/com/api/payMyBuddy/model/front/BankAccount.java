package com.api.payMyBuddy.model.front;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.StringJoiner;

/**
 * Represents the bank account of a user
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankAccount {

    /**
     * The bank name
     */
    @NotNull
    private String bank;

    /**
     * The IBAN
     */
    @NotNull
    private String iban;

    /**
     * The BIC
     */
    @NotNull
    private String bic;

    @Override
    public String toString() {
        return new StringJoiner(", ", "" + "{", "}")
                .add("\"bank\":\"" + bank + "\"")
                .add("\"iban\":\"" + iban + "\"")
                .add("\"bic\":\"" + bic + "\"")
                .toString();
    }
}
