package com.tortuca.lunchpicker.controller;

import com.tortuca.lunchpicker.model.User;
import com.tortuca.lunchpicker.repository.UserRepository;
import com.tortuca.lunchpicker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@CrossOrigin(origins = "${cors.origin}")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    @ResponseBody
    public ResponseEntity<User> createUser(@RequestBody String username) {
        Optional<User> saved = userService.createUser(username);

        return saved.isPresent()
            ? ResponseEntity.ok(saved.get())
            : ResponseEntity.notFound().build();
    }

    @PostMapping("/user/profile")
    @ResponseBody
    public ResponseEntity<User> getUser(@RequestBody String username) {
        Optional<User> found = userService.getUser(username);

        return found.isPresent()
                ? ResponseEntity.ok(found.get())
                : ResponseEntity.notFound().build();
    }
}
