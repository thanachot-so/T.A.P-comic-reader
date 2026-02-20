package com.tapcomiccomicreader.rest;

import com.tapcomiccomicreader.dto.AddFriendRequest;
import com.tapcomiccomicreader.dto.CreateUserRequest;
import com.tapcomiccomicreader.dto.FavoriteComicRequest;
import com.tapcomiccomicreader.dto.LoginRequest;
import com.tapcomiccomicreader.entity.User;
import com.tapcomiccomicreader.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserRestController {
    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{userUuid}")
    public User getUserByUUID(@PathVariable String userUuid) {
        return userService.findByUuid(userUuid);
    }

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody @Valid CreateUserRequest request) {
        userService.save(new User(request.getName(), request.getPassword()));
        return ResponseEntity.ok("user: " + request.getName() + " has been created");
    }

    @PostMapping("/add")
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

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable int userId) {
        userService.deleteById(userId);
        return ResponseEntity.ok("user id : " + userId + " has been deleted");
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Boolean>> login(@RequestBody @Valid LoginRequest request) {
        return userService.login(request);
    }

    @PostMapping("/follow")
    public ResponseEntity<Object> favorite(@RequestBody @Valid FavoriteComicRequest request) {
        userService.favorite(request);
        return ResponseEntity.ok("request success");
    }
}
