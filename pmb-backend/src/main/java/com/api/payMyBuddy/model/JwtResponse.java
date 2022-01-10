package com.api.payMyBuddy.model;

/**
 * Represents the authentication request response with token and user details
 */
public class JwtResponse {
    /**
     * The generated token
     */
    private String token;

    /**
     * The type of authentication
     */
    private String type = "Bearer";

    /**
     * The authenticated user email
     */
    private String email;

    public JwtResponse(String accessToken, String email) {
        this.token = accessToken;
        this.email = email;
    }

    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
