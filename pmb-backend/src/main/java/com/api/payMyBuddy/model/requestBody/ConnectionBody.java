package com.api.payMyBuddy.model.requestBody;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class ConnectionBody {

    @NotNull
    @NotEmpty
    String userEmail;

    @NotNull
    @NotEmpty
    String connectionEmail;

    public ConnectionBody(String userEmail, String connectionEmail) {
        this.userEmail = userEmail;
        this.connectionEmail = connectionEmail;
    }
}
