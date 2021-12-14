package com.api.payMyBuddy.model.front;

import com.api.payMyBuddy.model.entity.ConnectionEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Connection {

    @NotNull
    @NotEmpty
    private String userEmail;

    @NotNull
    @NotEmpty
    private String connectionEmail;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String name;

    public Connection(ConnectionEntity connectionEntity) {
        this.setUserEmail(connectionEntity.getConnectionPrimaryKey().getUserEntity().getEmail());
        this.setConnectionEmail(connectionEntity.getConnectionPrimaryKey().getUserEntityConnection().getEmail());
        this.setName(String.format("%s %s", connectionEntity.getConnectionPrimaryKey().getUserEntityConnection().getFirstName(), connectionEntity.getConnectionPrimaryKey().getUserEntityConnection().getLastName()));
    }

    @SneakyThrows
    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }
}
