package org.dsmhack.controller;

import org.dsmhack.model.CheckIn;
import org.dsmhack.model.Project;
import org.dsmhack.repository.CheckInRepository;
import org.dsmhack.repository.ProjectRepository;
import org.dsmhack.service.CodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private CheckInRepository checkInRepository;

    @Autowired
    private CodeGenerator codeGenerator;

    @GetMapping("/projects")
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @GetMapping("/projects/{projectId}")
    public Project getProjectById(@PathVariable String projectId) {
        return projectRepository.findOne(projectId);
    }

    @PostMapping("/projects")
    public Project save(Project project) {
        project.setProjId(codeGenerator.generateUUID());
        return projectRepository.save(project);
    }

    @GetMapping("/projects/{projectId}/check-ins")
    public List<CheckIn> findAllCheckins(@PathVariable String projectId) {
        return checkInRepository.findByProjGuid(projectId);
    }
}