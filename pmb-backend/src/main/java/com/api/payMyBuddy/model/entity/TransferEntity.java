package com.api.payMyBuddy.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="transfert")
@Getter
@Setter
@NoArgsConstructor
public class TransferEntity {

    @EmbeddedId
    private TransferPrimaryKey transferPrimaryKey;

    @Column(name="montant")
    private int Amount;

    @Column
    private String description;

    @Column(name="date_prelevement")
    private Date debitDate;

    @Column(name="facture_id")
    private String bill;
}
