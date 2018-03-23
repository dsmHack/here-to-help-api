package org.dsmhack.controller;

import org.dsmhack.model.Project;
import org.dsmhack.repository.ProjectRepository;
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
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private GuidGenerator guidGenerator;

    @GetMapping("/projects")
    public List<Project> getAllProjects() {
        Project fakeProject = createFakeProjectWith("project name");
        Project fakeProject1 = createFakeProjectWith("project name 2");
        return Arrays.asList(fakeProject, fakeProject1);
    }

    @GetMapping("/projects/{projectId}")
    public Project getProjectById(@PathVariable String projectId) {
        return createFakeProjectWith("project name and id: " + projectId);
    }

    @PostMapping("/projects")
    public Project save(Project project) {
        project.setProjectId(guidGenerator.generate());
        return projectRepository.save(project);
    }

    private Project createFakeProjectWith(String project_name) {
        Project project = new Project();
        project.setProjectId(new UUID(5l, 1l));
        project.setName(project_name);
        project.setDescription("project description");
        return project;
    }
}