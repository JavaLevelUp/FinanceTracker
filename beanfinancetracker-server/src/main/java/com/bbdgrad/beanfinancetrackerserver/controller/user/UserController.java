package com.bbdgrad.beanfinancetrackerserver.controller.user;

import com.bbdgrad.beanfinancetrackerserver.controller.category.CategoryRequest;
import com.bbdgrad.beanfinancetrackerserver.model.Batch;
import com.bbdgrad.beanfinancetrackerserver.model.Category;
import com.bbdgrad.beanfinancetrackerserver.model.User;
import com.bbdgrad.beanfinancetrackerserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1/user")
public class UserController {
    private final UserService userService;

    @GetMapping()
    public ResponseEntity<List<User>> getUser() {
        return userService.getUsers();
    }

    @PostMapping
    public ResponseEntity<User> createCategory(@RequestBody UserRequest userRequest) {
        return userService.registerUser(userRequest);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getBatch(@PathVariable("userId") Integer userId) {
        return userService.getUser(userId);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> removeBatch(@PathVariable("userId") int userId) {
        return userService.removeUser(userId);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<User> updateBatch(@PathVariable("userId") Integer userId,
                                             @RequestParam(required = false) Optional<String> name,
                                             @RequestParam(required = false) Optional<String> email) {
        return userService.updateUser(userId, name, email);
    }


}
