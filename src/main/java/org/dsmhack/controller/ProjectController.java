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

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

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

    @GetMapping("/projects/{projectId}")
    public Project getProjectById(@PathVariable String projectId) {
        return projectRepository.findOne(projectId);
    }

    @PostMapping("/projects")
    public Project save(@RequestBody Project project) {
        project.setProjGuid(codeGenerator.generateUUID());
        return projectRepository.save(project);
    }

    @GetMapping("/projects/{projectId}/check-ins")
    public List<CheckIn> findAllCheckins(@PathVariable String projectId) {
        return checkInRepository.findByProjGuid(projectId);
    }

    @PostMapping("/projects/{projectId}/check-ins")
    public CheckIn checkUserIn(@PathVariable String projectId, @RequestBody String userGuid){
        CheckIn checkIn = new CheckIn();
        checkIn.setUserGuid(userGuid);
        checkIn.setProjGuid(projectId);
        checkIn.setTimeIn(Timestamp.valueOf(LocalDateTime.now()));
        return checkInRepository.save(checkIn);
    }

    @PostMapping("/projects/{projectId}/user")
    public UserProject addUser(@PathVariable String projectId, @RequestBody String userGuid){
        UserProject userProject = new UserProject();
        UserProject.MyKey myKey = new UserProject.MyKey();
        myKey.setProjGuid(projectId);
        myKey.setUserGuid(userGuid);

        userProject.setMyKey(myKey);
        return userProjectRepository.save(userProject);
    }

    @PutMapping("/projects/{projectId}/check-ins")
    public CheckIn checkOutUser(@PathVariable String projectId, @RequestBody String userGuid){
        CheckIn checkIn = checkInRepository.findByProjGuidAndUserGuid(projectId, userGuid);
        checkIn.setTimeOut(Timestamp.valueOf(LocalDateTime.now()));
        return checkInRepository.save(checkIn);
    }
}