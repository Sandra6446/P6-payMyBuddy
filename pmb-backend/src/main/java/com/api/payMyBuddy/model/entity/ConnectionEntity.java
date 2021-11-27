package com.api.payMyBuddy.model.entity;

import lombok.*;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="reseau")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
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
