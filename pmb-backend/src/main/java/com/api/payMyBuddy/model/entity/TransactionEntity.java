package com.api.payMyBuddy.model.entity;

import com.api.payMyBuddy.model.front.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;


/**
 * Represents a transaction entity in database
 *
 * @see Transaction
 */
@Entity
@Table(name = "transfert")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEntity {

    /**
     * The composed primary key of table "transfert"
     */
    @EmbeddedId
    private TransactionPrimaryKey transferPrimaryKey;

    /**
     * The transaction amount
     */
    @Column(name = "montant")
    private int amount;

    /**
     * The transaction details
     */
    @Column
    private String description;

    /**
     * The billing date
     */
    @Column(name = "date_prelevement")
    private LocalDateTime debitDate;

    /**
     * The associated bill
     */
    @Column(name = "facture_id")
    private String bill;

    public TransactionEntity(UserEntity userEntity, UserEntity contactEntity, Transaction transaction) {
        TransactionPrimaryKey primaryKey = new TransactionPrimaryKey();
        primaryKey.setUserEntity(userEntity);
        primaryKey.setContactEntity(contactEntity);
        primaryKey.setDate(LocalDateTime.now());
        this.setTransferPrimaryKey(primaryKey);
        this.setAmount(transaction.getAmount());
        this.setDescription(transaction.getDescription());
    }
}
