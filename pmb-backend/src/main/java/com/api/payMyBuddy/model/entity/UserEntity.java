package com.api.payMyBuddy.model.entity;

import com.api.payMyBuddy.model.front.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user entity in database
 *
 * @see User
 */
@Entity
@Table(name = "utilisateur")
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
public class UserEntity implements Serializable {

    /**
     * The user Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /**
     * The user entity email, unique
     */
    @NotNull
    private String email;

    /**
     * The user entity first name
     */
    @Column(name = "prenom")
    private String firstName;

    /**
     * The user entity last name
     */
    @Column(name = "nom")
    private String lastName;

    /**
     * The user entity password
     */
    @Column(name = "mot_de_passe")
    private String password;

    /**
     * The user entity balance in the application
     */
    @Column(name = "solde")
    private double balance;

    /**
     * The user entity bank name
     */
    @Column(name = "banque")
    private String bank;

    /**
     * The user entity account iban
     */
    private String iban;

    /**
     * The user entity bank account bic
     */
    private String bic;

    /**
     * The user entity contacts list
     */
    @OneToMany(mappedBy = "connectionPrimaryKey.userEntity")
    private List<ConnectionEntity> connectionEntities = new ArrayList<>();

    /**
     * The user entity debits list
     */
    @OneToMany(mappedBy = "transferPrimaryKey.userEntity")
    private List<TransactionEntity> debits = new ArrayList<>();

    /**
     * The user entity credits list
     */
    @OneToMany(mappedBy = "transferPrimaryKey.contactEntity")
    private List<TransactionEntity> credits = new ArrayList<>();

    public UserEntity(User user) {
        this.setEmail(user.getEmail());
        this.setFirstName(user.getFirstName());
        this.setLastName(user.getLastName());
        this.setBank(user.getBankAccount().getBank());
        this.setBic(user.getBankAccount().getBic());
        this.setIban(user.getBankAccount().getIban());
    }

    /**
     * Updates the user profile
     *
     * @param user : The new profile to be registered
     */
    public void update(User user) {
        if (!user.getEmail().isEmpty()) {
            this.setEmail(user.getEmail());
        }
        if (!user.getFirstName().isEmpty()) {
            this.setFirstName(user.getFirstName());
        }
        if (!user.getLastName().isEmpty()) {
            this.setLastName(user.getLastName());
        }
        if (!user.getBankAccount().getBank().isEmpty()) {
            this.setBank(user.getBankAccount().getBank());
        }
        if (!user.getBankAccount().getIban().isEmpty()) {
            this.setIban(user.getBankAccount().getIban());
        }
        if (!user.getBankAccount().getBic().isEmpty()) {
            this.setBic(user.getBankAccount().getBic());
        }
    }

    /**
     * Updates the user balance
     *
     * @param amount : The amount to be added
     */
    public void updateBalance(int amount) {
        double newAmount = this.getBalance() + amount;
        this.setBalance(newAmount);
    }

}
