package com.tapcomiccomicreader.dto;

import jakarta.validation.constraints.NotBlank;

public class AddFriendRequest {
    @NotBlank
    private String userUuid;

    @NotBlank
    private String friendUuid;

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public String getFriendUuid() {
        return friendUuid;
    }

    public void setFriendUuid(String friendUuid) {
        this.friendUuid = friendUuid;
    }
}
