package com.tapcomiccomicreader.dto;

import jakarta.validation.constraints.NotBlank;

public class RequestTokenRefresh {
    @NotBlank
    private String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public String toString() {
        return "RequestTokenRefresh{" +
                "refreshToken='" + refreshToken + '\'' +
                '}';
    }
}
