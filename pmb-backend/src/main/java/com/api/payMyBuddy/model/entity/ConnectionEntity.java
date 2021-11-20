package com.api.payMyBuddy.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="reseau")
@Getter
@Setter
@NoArgsConstructor
public class ConnectionEntity {

    @EmbeddedId
    private ConnectionPrimaryKey connectionPrimaryKey;

    public ConnectionEntity(UserEntity userEntity, UserEntity userEntityConnection) {
        ConnectionPrimaryKey primaryKey = new ConnectionPrimaryKey();
        primaryKey.setUserEntity(userEntity);
        primaryKey.setUserEntityConnection(userEntityConnection);
        this.setConnectionPrimaryKey(primaryKey);
    }
}
