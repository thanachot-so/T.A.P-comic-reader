package com.tapcomiccomicreader.rest;

import com.tapcomiccomicreader.dto.AddFriendRequest;
import com.tapcomiccomicreader.dto.CreateUserRequest;
import com.tapcomiccomicreader.entity.User;
import com.tapcomiccomicreader.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@RequestBody @Valid CreateUserRequest request) {
        userService.save(new User(request.getName(), request.getPassword()));
        return ResponseEntity.ok("user: " + request.getName() + " has been created");
    }

    @PostMapping("/users/add")
    public String addUser(@RequestBody @Valid AddFriendRequest friendRequest) {
        var user = userService.findByUuid(friendRequest.getUserUuid());
        var friend = userService.findByUuid(friendRequest.getFriendUuid());
        boolean isAdd = userService.addFriend(user.getId(), friend.getId());

        if (isAdd) {
            return "user " + user.getName() + " added " + friend.getName();
        } else {
            return "user " + user.getName() + " removed " + friend.getName();
        }
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable int userId) {
        userService.deleteById(userId);
        return ResponseEntity.ok("user id : " + userId + " has been deleted");
    }
}
