package com.api.payMyBuddy.model.requestBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class TransactionBody extends ConnectionBody {

    @NotNull
    @NotEmpty
    String description;

    @NotNull
    Integer amount;

    public TransactionBody(String userEmail, String connectionEmail, String description, Integer amount) {
        super(userEmail, connectionEmail);
        this.description = description;
        this.amount = amount;
    }

    @SneakyThrows
    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }
}
