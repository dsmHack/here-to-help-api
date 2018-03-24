package org.dsmhack.controller;

import org.dsmhack.model.CheckIn;
import org.dsmhack.model.Project;
import org.dsmhack.model.User;
import org.dsmhack.model.UserProject;
import org.dsmhack.repository.CheckInRepository;
import org.dsmhack.repository.ProjectRepository;
import org.dsmhack.repository.UserProjectRepository;
import org.dsmhack.repository.UserRepository;
import org.dsmhack.service.CodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProjectRepository userProjectRepository;

    @Autowired
    private ProjectRepository projectRepository;
    private CheckInRepository checkInRepository;

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

    @GetMapping("/users/{userGuid}/projects")
    public User getUsersAndProjects(@PathVariable String userGuid) {
        User user = userRepository.findOne(userGuid);

        List<UserProject> userProjectList = userProjectRepository.findAll();
        userProjectList.forEach(userProject -> {
            if(userProject.getMyKey().getUserGuid().equals(userGuid)){
                Project project = projectRepository.findOne(userProject.getMyKey().getProjGuid());
                user.getProjectList().add(project);
            }
        });
        return user;
    }

    @GetMapping("/users/{userGuid}/check-ins")
    public List<CheckIn> getCheckins(@PathVariable("userGuid") String userGuid) {
        return checkInRepository.findByUserGuid(userGuid);
    }

    @GetMapping("/users/{userGuid}/check-ins/active")
    public List<CheckIn> getActiveCheckins(@PathVariable("userGuid") String userGuid) {
        return checkInRepository.findActiveByUserGuid(userGuid);
    }
}