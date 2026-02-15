package com.tapcomiccomicreader.service;

import com.tapcomiccomicreader.dao.UserRepository;
import com.tapcomiccomicreader.entity.User;
import com.tapcomiccomicreader.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
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
}
