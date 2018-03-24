package org.dsmhack.controller;

import org.dsmhack.model.User;
import org.dsmhack.repository.UserRepository;
import org.dsmhack.service.CodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CodeGenerator codeGenerator;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{userId}")
    public User getUserById(@PathVariable String userId) {
        return userRepository.findOne(userId);
    }

    @PostMapping("/users")
    public User save(@RequestBody User user) {
        user.setUserGuid(codeGenerator.generateUUID());
        return userRepository.save(user);
    }
}