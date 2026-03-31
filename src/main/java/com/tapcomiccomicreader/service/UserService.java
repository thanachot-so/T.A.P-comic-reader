package com.tapcomiccomicreader.service;

import com.tapcomiccomicreader.dto.FavoriteComicRequest;
import com.tapcomiccomicreader.dto.UserDTO;
import com.tapcomiccomicreader.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService{
    void save(User user);

    void create(String name, String password);

    void deleteById(int id);

    List<User> findAll();

    User findById(int id);

    User findByUuid(String uuid);

    boolean addFriend(int userId, int friendId);

    void favorite(FavoriteComicRequest request);

    Page<UserDTO> search(String keyword, int pageNumber);

    Page<UserDTO> findFriends(String uuid, int pageNumber);

    void switchPrivate();
}
