package com.tapcomiccomicreader.rest;

import com.tapcomiccomicreader.dto.*;
import com.tapcomiccomicreader.entity.User;
import com.tapcomiccomicreader.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public List<UserDTO> findAll() {
        return userService.findAll().stream()
                .map(UserDTO::new)
                .toList();
    }

    @GetMapping("/{userUuid}")
    public User getUserByUUID(@PathVariable String userUuid) {
        return userService.findByUuid(userUuid);
    }

    @GetMapping("/{userUuid}/library")
    public LibraryDTO getUserLibrary(@PathVariable String userUuid) {
        var user = userService.findByUuid(userUuid);
        return new LibraryDTO(user);
    }

    @GetMapping("/{userUuid}/friends")
    public Page<UserDTO> getUserFriends(@PathVariable String userUuid,
                                        @RequestParam(defaultValue = "0") int page) {
        return userService.findFriends(userUuid, page);
    }

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody @Valid CreateUserRequest request) {
        userService.create(request.getName(), request.getPassword());
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

    @PostMapping("/search")
    public Page<UserDTO> search(@RequestBody @Valid SearchUserRequest request,
                                @RequestParam(defaultValue = "0") int page) {
        return userService.search(request.getKeyword(), page);
    }
}
