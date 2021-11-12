package com.api.payMyBuddy.model.entity;

import com.api.payMyBuddy.model.front.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class NetworkPrimaryKey implements Serializable {

    @ManyToOne
    @JoinColumn(name="utilisateur_email")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name="contact_email")
    private UserEntity connection;

}
