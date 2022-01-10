package com.api.payMyBuddy.model.front;

import com.api.payMyBuddy.model.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;

/**
 * Represents the identifiers of a user
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Login implements UserDetails {

    /**
     * The user email
     */
    @NotNull
    @NotEmpty
    protected String email;

    /**
     * The user password
     */
    @NotNull
    @NotEmpty
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    protected String password;

    /**
     * The user password confirmation for registration or profile's modification
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    protected String confirmPassword;

    public static Login build(UserEntity userEntity) {
        return new Login(
                userEntity.getEmail(),
                userEntity.getPassword(),
                null);
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return this.email;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}
