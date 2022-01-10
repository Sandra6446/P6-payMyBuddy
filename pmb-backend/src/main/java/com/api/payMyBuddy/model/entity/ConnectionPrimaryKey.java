package com.api.payMyBuddy.model.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Represents the composed primary key of table "reseau"
 */
@Embeddable
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class ConnectionPrimaryKey implements Serializable {

    /**
     * The current user entity
     */
    @ManyToOne
    @JoinColumn(name = "utilisateur_email", referencedColumnName = "email")
    private UserEntity userEntity;

    /**
     * The contact entity
     */
    @ManyToOne
    @JoinColumn(name = "contact_email", referencedColumnName = "email")
    private UserEntity contactEntity;

}
