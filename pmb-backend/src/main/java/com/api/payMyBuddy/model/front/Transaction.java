package com.api.payMyBuddy.model.front;

import com.api.payMyBuddy.model.entity.TransactionEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.StringJoiner;

/**
 * Represents a transaction
 *
 * @see TransactionEntity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    /**
     * Type of transfer
     */
    public enum Type {DEBIT, CREDIT}

    /**
     * The current user email
     */
    @NotNull
    @NotEmpty
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String userEmail;

    /**
     * The receiver email
     */
    @NotNull
    @NotEmpty
    private String connectionEmail;

    /**
     * The receiver name
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String connectionName;

    /**
     * The transaction details
     */
    @NotNull
    @NotEmpty
    private String description;

    /**
     * The transaction type
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Type type;

    /**
     * The transaction amount
     */
    @NotNull
    @Positive
    private Integer amount;

    /**
     * The transaction date
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime date;

    public Transaction(TransactionEntity transactionEntity, boolean isDebit) {
        if (isDebit) {
            this.setUserEmail(transactionEntity.getTransferPrimaryKey().getUserEntity().getEmail());
            this.setConnectionEmail(transactionEntity.getTransferPrimaryKey().getContactEntity().getEmail());
            this.setConnectionName(String.format("%s %s", transactionEntity.getTransferPrimaryKey().getContactEntity().getFirstName(), transactionEntity.getTransferPrimaryKey().getContactEntity().getLastName()));
            this.setType(Type.DEBIT);
        } else {
            this.setUserEmail(transactionEntity.getTransferPrimaryKey().getContactEntity().getEmail());
            this.setConnectionEmail(transactionEntity.getTransferPrimaryKey().getUserEntity().getEmail());
            this.setConnectionName(String.format("%s %s", transactionEntity.getTransferPrimaryKey().getUserEntity().getFirstName(), transactionEntity.getTransferPrimaryKey().getUserEntity().getLastName()));
            this.setType(Type.CREDIT);
        }
        this.setAmount(transactionEntity.getAmount());
        this.setDescription(transactionEntity.getDescription());
        this.setDate(transactionEntity.getTransferPrimaryKey().getDate());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", "" + "{", "}")
                .add("\"userEmail\":\"" + userEmail + "\"")
                .add("\"connectionEmail\":\"" + connectionEmail + "\"")
                .add("\"connectionName\":\"" + connectionName + "\"")
                .add("\"description\":\"" + description + "\"")
                .add("\"type\":\"" + type + "\"")
                .add("\"amount\":" + amount)
                .add("\"date\":\"" + date + "\"")
                .toString();
    }
}
