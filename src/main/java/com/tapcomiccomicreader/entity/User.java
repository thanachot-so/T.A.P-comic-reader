package com.tapcomiccomicreader.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "uuid", unique = true)
    private String uuid;

    @Column(name = "token")
    @JsonIgnore
    private String token;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    @JsonIgnore
    private String password;

    @ManyToMany
    @JoinTable(
            name = "user_follows",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "comic_id")
    )
    private List<Comic> followedComics;

    @ManyToMany
    @JoinTable(
        name = "user_friends",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private List<User> followedFriends;

    public User() {
        this.uuid = java.util.UUID.randomUUID().toString();
        this.followedComics = new ArrayList<>();
        this.followedFriends = new ArrayList<>();
    }

    public User(String name, String password) {
        this();
        this.name = name;
        this.password = password;
    }

    public boolean addFriend(User friend) {
        if (!followedFriends.contains(friend)) {
            followedFriends.add(friend);
            friend.getFollowedFriends().add(this);
            return true;
        } else {
            followedFriends.remove(friend);
            friend.getFollowedFriends().remove(this);
            return false;
        }
    }

    public void removeFriend(User friend) {
        followedFriends.remove(friend);
        friend.getFollowedFriends().remove(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Comic> getFollowedComics() {
        return followedComics;
    }

    public void setFollowedComics(List<Comic> followedComics) {
        this.followedComics = followedComics;
    }

    public List<User> getFollowedFriends() {
        return followedFriends;
    }

    public void setFollowedFriends(List<User> followedFriends) {
        this.followedFriends = followedFriends;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", UUID='" + uuid + '\'' +
                ", token='" + token + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", followedComics=" + followedComics +
                ", followedFriends=" + followedFriends +
                '}';
    }
}
