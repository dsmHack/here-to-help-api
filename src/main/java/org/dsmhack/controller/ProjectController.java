package org.dsmhack.controller;

import org.dsmhack.model.Project;
import org.dsmhack.repository.ProjectRepository;
import org.dsmhack.service.GuidGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private GuidGenerator guidGenerator;

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
        project.setProjId(guidGenerator.generate());
        return projectRepository.save(project);
    }
}