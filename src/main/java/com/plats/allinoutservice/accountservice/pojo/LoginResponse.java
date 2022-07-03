package com.plats.allinoutservice.accountservice.pojo;

public class LoginResponse {

    private final boolean status;
    private String secretString;
    private final String username;

    public LoginResponse(boolean status, String username) {
        this.status = status;
        this.username = username;
    }

    public void setSecretString(String secretString) {
        this.secretString = secretString;
    }

    public boolean getStatus() {
        return status;
    }

    public String getSecretString() {
        return secretString;
    }

    public String getUsername() {
        return username;
    }
}
