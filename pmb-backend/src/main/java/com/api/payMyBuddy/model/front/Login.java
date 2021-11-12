package com.api.payMyBuddy.model.front;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class Login {

    @NotNull
    private String email;

    @NotNull
    private String password;

    private String confirmPassword;

}
