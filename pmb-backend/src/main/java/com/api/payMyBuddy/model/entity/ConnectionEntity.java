package com.api.payMyBuddy.model.entity;

import lombok.*;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Represents a connection entity in database
 *
 * @see com.api.payMyBuddy.model.front.Connection
 */
@Entity
@Table(name = "reseau")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ConnectionEntity {

    /**
     * The composed primary key of table "reseau"
     *
     * @see ConnectionPrimaryKey
     */
    @EmbeddedId
    private ConnectionPrimaryKey connectionPrimaryKey;

    public ConnectionEntity(UserEntity userEntity, UserEntity contactEntity) {
        ConnectionPrimaryKey primaryKey = new ConnectionPrimaryKey();
        primaryKey.setUserEntity(userEntity);
        primaryKey.setContactEntity(contactEntity);
        this.setConnectionPrimaryKey(primaryKey);
    }

}
