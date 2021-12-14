package com.api.payMyBuddy.model.front;

import com.api.payMyBuddy.model.entity.TransactionEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    public enum Type {DEBIT,CREDIT};

    @NotNull
    @NotEmpty
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String userEmail;

    @NotNull
    @NotEmpty
    private String connectionEmail;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String connectionName;

    @NotNull
    @NotEmpty
    private String description;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Type type;

    @NotNull
    @Positive
    private Integer amount;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    LocalDateTime date;

    public Transaction(TransactionEntity transactionEntity, boolean isDebit) {
        if (isDebit) {
            this.setConnectionEmail(transactionEntity.getTransferPrimaryKey().getUserEntityConnection().getEmail());
            this.setConnectionName(String.format("%s %s", transactionEntity.getTransferPrimaryKey().getUserEntityConnection().getFirstName(),transactionEntity.getTransferPrimaryKey().getUserEntityConnection().getLastName()));
            this.setType(Type.DEBIT);
        } else {
            this.setConnectionEmail(transactionEntity.getTransferPrimaryKey().getUserEntity().getEmail());
            this.setConnectionName(String.format("%s %s", transactionEntity.getTransferPrimaryKey().getUserEntity().getFirstName(),transactionEntity.getTransferPrimaryKey().getUserEntity().getLastName()));
            this.setType(Type.CREDIT);
        }
        this.setAmount(transactionEntity.getAmount());
        this.setDescription(transactionEntity.getDescription());
        this.setDate(transactionEntity.getTransferPrimaryKey().getDate());
    }

    @SneakyThrows
    @Override
    public String toString() {
        JsonMapper jsonMapper = new JsonMapper();
        return jsonMapper.writeValueAsString(this);
    }
}
