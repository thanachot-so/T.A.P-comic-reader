package com.tapcomiccomicreader.service;

import com.tapcomiccomicreader.dao.UserRepository;
import com.tapcomiccomicreader.dto.FavoriteComicRequest;
import com.tapcomiccomicreader.dto.LoginRequest;
import com.tapcomiccomicreader.dto.UserDTO;
import com.tapcomiccomicreader.entity.User;
import com.tapcomiccomicreader.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final ComicService comicService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ComicService comicService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.comicService = comicService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void create(String name, String password) {
        if (userRepository.isExist(name)) {
            throw new RuntimeException("username has been taken");
        }

        String hashedPassword = passwordEncoder.encode(password);
        User user = new User(name, hashedPassword);

        user.setRole("USER");

        save(user);
    }

    @Override
    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("could not find user with the id - " + id));
    }

    @Override
    public User findByUuid(String uuid) {
        return userRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("could not find user with the uuid - " + uuid));
    }

    @Override
    @Transactional
    public boolean addFriend(int userId, int friendId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("could not find user with the id - " + userId));
        var friend = userRepository.findById(friendId)
                .orElseThrow(() -> new ResourceNotFoundException("could not find friend with the id - " + friendId));

        boolean isAdd = user.addFriend(friend);

        userRepository.save(user);

        return isAdd;
    }

    @Override
    @Transactional
    public void favorite(FavoriteComicRequest request) {
        var userId = findByUuid(request.getUserUuid()).getId();
        var comicId = comicService.findByUuid(request.getComicUuid()).getId();

        if (userRepository.isFollowed(userId, comicId)) {
            userRepository.removeFollowed(userId, comicId);
        } else {
            userRepository.followComic(userId, comicId);
        }
    }

    @Override
    public Page<UserDTO> search(String keyword, int pageNumber) {
        Integer currentUserId = SecurityUtils.getCurrentUserId();

        int pageSize = 20;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        var userPages = userRepository.searchByName(keyword, currentUserId, pageable);

        return userPages.map(UserDTO::new);
    }

    @Override
    public Page<UserDTO> findFriends(String uuid, int pageNumber) {
        var user = findByUuid(uuid);
        int pageSize = 10;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        var friendPages = userRepository.findFriend(user.getId(), pageable);

        return friendPages.map(UserDTO::new);
    }
}
