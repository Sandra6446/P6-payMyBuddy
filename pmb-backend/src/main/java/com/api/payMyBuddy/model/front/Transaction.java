package com.api.payMyBuddy.model.front;

import com.api.payMyBuddy.model.entity.TransactionEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
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
    Date date;

    public Transaction(TransactionEntity transferEntity, boolean isDebit) {
        if (isDebit) {
            this.setConnectionEmail(transferEntity.getTransferPrimaryKey().getUserEntityConnection().getEmail());
            this.setAmount(-transferEntity.getAmount());
        } else {
            this.setConnectionEmail(transferEntity.getTransferPrimaryKey().getUserEntity().getEmail());
            this.setAmount(transferEntity.getAmount());
        }
        this.setDescription(transferEntity.getDescription());
        this.setDate(transferEntity.getTransferPrimaryKey().getDate());
    }

}
