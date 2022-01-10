package com.api.payMyBuddy.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Represents the composed primary key of table "transfert"
 */
@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionPrimaryKey implements Serializable {

    /**
     * The current user entity
     */
    @ManyToOne(
            cascade = {
                    CascadeType.MERGE
            }
    )
    @JoinColumn(name = "utilisateur_email", referencedColumnName = "email")
    private UserEntity userEntity;

    /**
     * The receiver contact entity
     */
    @ManyToOne(
            cascade = {
                    CascadeType.MERGE
            }
    )
    @JoinColumn(name = "destinataire_email", referencedColumnName = "email")
    private UserEntity contactEntity;

    /**
     * The transaction date
     */
    @Column
    private LocalDateTime date;

}
