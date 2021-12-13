package com.api.payMyBuddy.model.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class ConnectionPrimaryKey implements Serializable {

    @ManyToOne
    @JoinColumn(name = "utilisateur_email", referencedColumnName = "email")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "contact_email", referencedColumnName = "email")
    private UserEntity userEntityConnection;

}
