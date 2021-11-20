package com.api.payMyBuddy.model.requestBody;

import com.api.payMyBuddy.model.front.Transaction;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
public class TransactionBody extends Transaction {

    @NotNull
    @NotEmpty
    String userEmail;

}
