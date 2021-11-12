package com.api.payMyBuddy.model.front;

import com.api.payMyBuddy.model.entity.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class Connection {

    @NotNull
    String email;

    @NotNull
    String name;

    public Connection(UserEntity userEntity) {
        this.setEmail(userEntity.getEmail());
        this.setName(userEntity.getFirstName() + " " + userEntity.getLastName());
    }
}
