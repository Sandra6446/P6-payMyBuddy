package com.api.payMyBuddy.model.front;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class Transaction {

    @NotNull
    String connection_email;

    @NotNull
    String description;

    @NotNull
    Integer amount;

    @NotNull
    Date date;
}
