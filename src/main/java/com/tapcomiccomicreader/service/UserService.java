package com.tapcomiccomicreader.service;

import com.tapcomiccomicreader.entity.User;

import java.util.List;

public interface UserService{
    void save(User user);

    void deleteById(int id);

    List<User> findAll();

    User findById(int id);

    User findByUuid(String uuid);

    boolean addFriend(int userId, int friendId);
}
