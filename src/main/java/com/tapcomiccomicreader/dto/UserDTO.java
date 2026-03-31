package com.tapcomiccomicreader.dto;

import com.tapcomiccomicreader.entity.User;

public class UserDTO {
    private String name;
    private String uuid;
    private Integer followedComicCount;
    private Integer friends;
    private boolean isPrivate;

    public UserDTO(User user) {
        this.name = user.getName();
        this.uuid = user.getUuid();
        this.followedComicCount = user.getFollowedComicsCount();
        this.friends = user.getFriends();
        this.isPrivate = user.isPrivate();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getFollowedComicCount() {
        return followedComicCount;
    }

    public void setFollowedComicCount(Integer followedComicCount) {
        this.followedComicCount = followedComicCount;
    }

    public Integer getFriends() {
        return friends;
    }

    public void setFriends(Integer friends) {
        this.friends = friends;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "name='" + name + '\'' +
                ", uuid='" + uuid + '\'' +
                ", followedComicCount=" + followedComicCount +
                ", friends=" + friends +
                ", isPrivate=" + isPrivate +
                '}';
    }
}
