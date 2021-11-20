package com.api.payMyBuddy.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Date;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class TransactionPrimaryKey implements Serializable {

    @ManyToOne
    @JoinColumn(name="utilisateur_email")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name="destinataire_email")
    private UserEntity userEntityConnection;

    @Column
    private Date date;

}
