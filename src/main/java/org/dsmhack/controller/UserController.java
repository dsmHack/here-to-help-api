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
        User fakeUser = createFakeUserWith("Joe Doe");
        User fakeUser1 = createFakeUserWith("Joe Doe 2");
        return Arrays.asList(fakeUser, fakeUser1);
    }

    @GetMapping("/users/{userId}")
    public User getUserById(@PathVariable String userId) {
        return createFakeUserWith("user name and id: " + userId);
    }

    @PostMapping("/users")
    public User save(User user) {
        user.setUserId(guidGenerator.generate());
        return userRepository.save(user);
    }

    private User createFakeUserWith(String firstName) {
        User user = new User();
        user.setUserId("userId");
        user.setFirstName(firstName);
        user.setLastName("user description");
        return user;
    }
}