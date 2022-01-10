package com.api.payMyBuddy.model.front;

import com.api.payMyBuddy.model.entity.ConnectionEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.StringJoiner;

/**
 * Represents a user and his contact
 *
 * @see ConnectionEntity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Connection {

    /**
     * The current user email
     */
    @NotNull
    @NotEmpty
    private String userEmail;

    /**
     * The contact email
     */
    @NotNull
    @NotEmpty
    private String connectionEmail;

    /**
     * The contact name
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String name;

    public Connection(ConnectionEntity connectionEntity) {
        this.setUserEmail(connectionEntity.getConnectionPrimaryKey().getUserEntity().getEmail());
        this.setConnectionEmail(connectionEntity.getConnectionPrimaryKey().getContactEntity().getEmail());
        this.setName(String.format("%s %s", connectionEntity.getConnectionPrimaryKey().getContactEntity().getFirstName(), connectionEntity.getConnectionPrimaryKey().getContactEntity().getLastName()));
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", "" + "{", "}")
                .add("\"userEmail\":\"" + userEmail + "\"")
                .add("\"connectionEmail\":\"" + connectionEmail + "\"")
                .add("\"name\":\"" + name + "\"")
                .toString();
    }
}
