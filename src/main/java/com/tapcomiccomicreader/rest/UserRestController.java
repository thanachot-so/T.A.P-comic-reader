package com.tapcomiccomicreader.rest;

import com.tapcomiccomicreader.dto.AddFriendRequest;
import com.tapcomiccomicreader.entity.User;
import com.tapcomiccomicreader.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserRestController {
    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/users/{userUuid}")
    public User getUserByUUID(@PathVariable String userUuid) {
        return userService.findByUuid(userUuid);
    }

    @PostMapping("/users/add")
    public String addUser(@RequestBody AddFriendRequest friendRequest) {
        var user = userService.findByUuid(friendRequest.getUserUuid());
        var friend = userService.findByUuid(friendRequest.getFriendUuid());
        boolean isAdd = userService.addFriend(user.getId(), friend.getId());

        if (isAdd) {
            return "user " + user.getName() + " added " + friend.getName();
        } else {
            return "user " + user.getName() + " removed " + friend.getName();
        }
    }
}
