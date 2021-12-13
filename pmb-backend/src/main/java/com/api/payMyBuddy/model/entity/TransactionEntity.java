package com.api.payMyBuddy.model.entity;

import com.api.payMyBuddy.model.requestBody.TransactionBody;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name="transfert")
@Getter
@Setter
@NoArgsConstructor
public class TransactionEntity {

    @EmbeddedId
    private TransactionPrimaryKey transferPrimaryKey;

    @Column(name="montant")
    private int amount;

    @Column
    private String description;

    @Column(name="date_prelevement")
    private LocalDateTime debitDate;

    @Column(name="facture_id")
    private String bill;

    public TransactionEntity(UserEntity userEntity, UserEntity userEntityConnection, TransactionBody transactionBody) {
        TransactionPrimaryKey primaryKey = new TransactionPrimaryKey();
        primaryKey.setUserEntity(userEntity);
        primaryKey.setUserEntityConnection(userEntityConnection);
        primaryKey.setDate(LocalDateTime.now());
        this.setTransferPrimaryKey(primaryKey);
        this.setAmount(transactionBody.getAmount());
        this.setDescription(transactionBody.getDescription());
    }
}
