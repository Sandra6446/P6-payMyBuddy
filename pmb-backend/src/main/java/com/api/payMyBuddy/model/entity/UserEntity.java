package com.api.payMyBuddy.model.entity;

import com.api.payMyBuddy.model.front.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "utilisateur")
@Getter
@Setter
@ToString
@DynamicUpdate
public class UserEntity {

    @Id
    @NotNull
    private String email;

    @Column(name = "nom")
    private String firstName;

    @Column(name = "prenom")
    private String lastName;

    @Column(name = "mot_de_passe")
    private String password;

    @Column(name = "solde")
    private float balance;

    @Column(name = "banque")
    private String bank;

    private String iban;

    private String bic;

    @OneToMany(mappedBy = "networkPrimaryKey.user")
    private List<NetworkEntity> networkEntities;

    @OneToMany(mappedBy = "transferPrimaryKey.user")
    private List<TransferEntity> transferEntities;

    public UserEntity() {
        this.setNetworkEntities(new ArrayList<>());
        this.setTransferEntities(new ArrayList<>());
    }

    public UserEntity(User user) {
        this.setEmail(user.getEmail());
        this.setFirstName(user.getFirstName());
        this.setLastName(user.getLastName());
        this.setPassword(user.getPassword());
        this.setBank(user.getBankAccount().getBank());
        this.setBic(user.getBankAccount().getBic());
        this.setIban(user.getBankAccount().getIban());
    }

}
