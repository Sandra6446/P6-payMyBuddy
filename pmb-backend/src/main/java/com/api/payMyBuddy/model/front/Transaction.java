package com.api.payMyBuddy.model.front;

import com.api.payMyBuddy.model.entity.TransactionEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @NotNull
    @NotEmpty
    String connectionEmail;

    @NotNull
    @NotEmpty
    String description;

    @NotNull
    Integer amount;

    @NotNull
    LocalDateTime date;

    public Transaction(TransactionEntity transactionEntity, boolean isDebit) {
        if (isDebit) {
            this.setConnectionEmail(transactionEntity.getTransferPrimaryKey().getUserEntityConnection().getEmail());
            this.setAmount(-transactionEntity.getAmount());
        } else {
            this.setConnectionEmail(transactionEntity.getTransferPrimaryKey().getUserEntity().getEmail());
            this.setAmount(transactionEntity.getAmount());
        }
        this.setDescription(transactionEntity.getDescription());
        this.setDate(transactionEntity.getTransferPrimaryKey().getDate());
    }

    @Override
    public String toString() {
        return String.format("{\"connectionEmail\":\"%s\", \"description\":\"%s\", \"amount\":%d, \"date\":\"%s\"}", connectionEmail, description, amount, date);
    }
}
