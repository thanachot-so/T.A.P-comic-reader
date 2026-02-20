package com.tapcomiccomicreader.dto;

import jakarta.validation.constraints.NotBlank;

public class FavoriteComicRequest {
    @NotBlank
    private String userUuid;

    @NotBlank
    private String comicUuid;

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public String getComicUuid() {
        return comicUuid;
    }

    public void setComicUuid(String comicUuid) {
        this.comicUuid = comicUuid;
    }

    @Override
    public String toString() {
        return "FavoriteComicRequest{" +
                "userUuid='" + userUuid + '\'' +
                ", comicUuid='" + comicUuid + '\'' +
                '}';
    }
}
