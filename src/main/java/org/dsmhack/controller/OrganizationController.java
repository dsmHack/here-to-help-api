package org.dsmhack.controller;

import org.dsmhack.model.Organization;
import org.dsmhack.model.Project;
import org.dsmhack.repository.OrganizationRepository;
import org.dsmhack.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrganizationController {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @GetMapping("/organizations")
    public List<Organization> getAllOrganizations() {
        return organizationRepository.findAll();
    }

    @GetMapping("/organizations/{organizationId}")
    public Organization getOrganizationBy(@PathVariable String organizationId) {
        return organizationRepository.findOne(organizationId);
    }

    @GetMapping("/organizations/{organizationId}/projects")
    public List<Project> findProjectsForOrganization(@PathVariable String organizationId) {
        return projectRepository.findByOrgGuid(organizationId);
    }
}