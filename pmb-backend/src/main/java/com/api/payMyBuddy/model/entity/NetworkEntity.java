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
public class NetworkEntity {

    @EmbeddedId
    private NetworkPrimaryKey networkPrimaryKey;

    public NetworkEntity(UserEntity user, UserEntity connection) {
        NetworkPrimaryKey primaryKey = new NetworkPrimaryKey();
        primaryKey.setUser(user);
        primaryKey.setConnection(connection);
        this.setNetworkPrimaryKey(primaryKey);
    }
}
