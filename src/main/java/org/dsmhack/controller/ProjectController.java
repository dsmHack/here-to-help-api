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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private CheckInRepository checkInRepository;

    @Autowired
    private CodeGenerator codeGenerator;

    @Autowired
    private UserProjectRepository userProjectRepository;

    @GetMapping("/projects")
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @GetMapping("/projects/{projectGuid}")
    public Project getProjectById(@PathVariable String projectGuid) {
        return projectRepository.findOne(projectGuid);
    }

    @PostMapping("/projects")
    public ResponseEntity<Project> save(@Validated @RequestBody Project project) {
        project.setProjGuid(codeGenerator.generateUUID());
        return new ResponseEntity<>(projectRepository.save(project), HttpStatus.CREATED);
    }

    @GetMapping("/projects/{projectGuid}/check-ins")
    public List<CheckIn> findAllCheckins(@PathVariable String projectGuid) {
        return checkInRepository.findByProjGuid(projectGuid);
    }

    @PostMapping("/projects/{projectGuid}/check-ins")
    public ResponseEntity<CheckIn> checkUserIn(@PathVariable UUID projectGuid, @RequestBody UUID userGuid){
        CheckIn checkIn = new CheckIn();
        checkIn.setUserGuid(userGuid);
        checkIn.setProjGuid(projectGuid);
        checkIn.setTimeIn(LocalDateTime.now());
        return new ResponseEntity<>(checkInRepository.save(checkIn), HttpStatus.CREATED);
    }

    @PostMapping("/projects/{projectGuid}/user")
    public UserProject addUser(@PathVariable UUID projectGuid, @RequestBody UUID userGuid){
        UserProject userProject = new UserProject();
        UserProject.MyKey myKey = new UserProject.MyKey();
        myKey.setProjGuid(projectGuid);
        myKey.setUserGuid(userGuid);

        userProject.setMyKey(myKey);
        return userProjectRepository.save(userProject);
    }

    @PutMapping("/projects/{projectGuid}/check-ins")
    public CheckIn checkOutUser(@PathVariable String projectGuid, @RequestBody String userGuid){
        CheckIn checkIn = checkInRepository.findByProjGuidAndUserGuid(projectGuid, userGuid);
        checkIn.setTimeOut(LocalDateTime.now());
        return checkInRepository.save(checkIn);
    }
}