package com.api.payMyBuddy.model.entity;

import com.api.payMyBuddy.model.front.User;
import com.api.payMyBuddy.model.requestBody.UserProfile;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "utilisateur")
@Data
@NoArgsConstructor
@DynamicUpdate
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int userId;

    @NotNull
    private String email;

    @Column(name = "nom")
    private String firstName;

    @Column(name = "prenom")
    private String lastName;

    @Column(name = "mot_de_passe")
    private String password;

    @Column(name = "solde")
    private double balance;

    @Column(name = "banque")
    private String bank;

    private String iban;

    private String bic;

    @OneToMany(mappedBy = "connectionPrimaryKey.userEntity")
    private List<ConnectionEntity> connectionEntities = new ArrayList<>();

    @OneToMany(mappedBy = "transferPrimaryKey.userEntity")
    private List<TransactionEntity> debits = new ArrayList<>();

    @OneToMany(mappedBy = "transferPrimaryKey.userEntityConnection")
    private List<TransactionEntity> credits = new ArrayList<>();

    public UserEntity(String email, String firstName, String lastName, String password, double balance, String bank, String iban, String bic, List<ConnectionEntity> connectionEntities, List<TransactionEntity> debits, List<TransactionEntity> credits) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.balance = balance;
        this.bank = bank;
        this.iban = iban;
        this.bic = bic;
        this.connectionEntities = connectionEntities;
        this.debits = debits;
        this.credits = credits;
    }

    public UserEntity(User user) {
        this.setEmail(user.getEmail());
        this.setFirstName(user.getFirstName());
        this.setLastName(user.getLastName());
        this.setPassword(user.getPassword());
        this.setBalance(user.getBalance());
        this.setBank(user.getBankAccount().getBank());
        this.setBic(user.getBankAccount().getBic());
        this.setIban(user.getBankAccount().getIban());
        // TODO List of connections, credits, debits
    }

    public void update(UserProfile userProfile) {
        if (!userProfile.getEmail().isEmpty()) {
            this.setEmail(userProfile.getEmail());
        }
        if (!userProfile.getFirstName().isEmpty()) {
            this.setFirstName(userProfile.getFirstName());
        }
        if (!userProfile.getLastName().isEmpty()) {
            this.setLastName(userProfile.getLastName());
        }
        if (!userProfile.getPassword().isEmpty()) {
            this.setPassword(userProfile.getPassword());
        }
        if (!userProfile.getBankAccount().getBank().isEmpty()) {
            this.setBank(userProfile.getBankAccount().getBank());
        }
        if (!userProfile.getBankAccount().getIban().isEmpty()) {
            this.setIban(userProfile.getBankAccount().getIban());
        }
        if (!userProfile.getBankAccount().getBic().isEmpty()) {
            this.setBic(userProfile.getBankAccount().getBic());
        }
    }

}
