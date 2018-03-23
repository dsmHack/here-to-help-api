package org.dsmhack.controller;

import org.dsmhack.model.User;
import org.dsmhack.model.User;
import org.dsmhack.repository.UserRepository;
import org.dsmhack.repository.UserRepository;
import org.dsmhack.service.GuidGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GuidGenerator guidGenerator;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{userId}")
    public User getUserById(@PathVariable String userId) {
        return userRepository.findOne(userId);
    }

    @PostMapping("/users")
    public User save(User user) {
        user.setUserId(guidGenerator.generate());
        return userRepository.save(user);
    }
}