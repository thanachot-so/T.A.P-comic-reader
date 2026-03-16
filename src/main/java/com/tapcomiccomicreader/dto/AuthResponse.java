package com.tapcomiccomicreader.dto;

import jakarta.validation.constraints.NotNull;

public class AuthResponse {
    @NotNull
    private String accessToken;

    @NotNull
    private String refreshToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String token) {
        this.accessToken = accessToken;
    }

    public AuthResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String token) {
        this.refreshToken = refreshToken;
    }

    @Override
    public String toString() {
        return "AuthResponse{" +
                "accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                '}';
    }
}
