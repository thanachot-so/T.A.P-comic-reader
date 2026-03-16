package com.tapcomiccomicreader.service;

import com.tapcomiccomicreader.dto.FavoriteComicRequest;
import com.tapcomiccomicreader.dto.LoginRequest;
import com.tapcomiccomicreader.dto.UserDTO;
import com.tapcomiccomicreader.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface UserService{
    void save(User user);

    void create(String name, String password);

    void deleteById(int id);

    List<User> findAll();

    User findById(int id);

    User findByUuid(String uuid);

    boolean addFriend(int userId, int friendId);

    ResponseEntity<Map<String, Boolean>> login(LoginRequest request);

    void favorite(FavoriteComicRequest request);

    Page<UserDTO> search(String keyword, int pageNumber);

    Page<UserDTO> findFriends(String uuid, int pageNumber);
}
