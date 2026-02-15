package com.tapcomiccomicreader.dto;

public class AddFriendRequest {
    private String userUuid;
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
