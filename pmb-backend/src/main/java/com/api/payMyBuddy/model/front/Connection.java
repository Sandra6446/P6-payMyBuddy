package com.api.payMyBuddy.model.front;

import com.api.payMyBuddy.model.entity.ConnectionEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class Connection {

    @NotNull
    @NotEmpty
    String email;

    @NotNull
    @NotEmpty
    String name;

    public Connection(ConnectionEntity connectionEntity) {
        this.setEmail(connectionEntity.getConnectionPrimaryKey().getUserEntityConnection().getEmail());
        this.setName(String.format("%s %s", connectionEntity.getConnectionPrimaryKey().getUserEntityConnection().getFirstName(), connectionEntity.getConnectionPrimaryKey().getUserEntityConnection().getLastName()));
    }

    public Connection(String email, String name) {
        this.email = email;
        this.name = name;
    }
}
