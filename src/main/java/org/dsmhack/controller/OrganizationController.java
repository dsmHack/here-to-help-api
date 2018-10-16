package org.dsmhack.controller;

import org.dsmhack.model.Organization;
import org.dsmhack.model.Project;
import org.dsmhack.repository.OrganizationRepository;
import org.dsmhack.repository.ProjectRepository;
import org.dsmhack.service.CodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class OrganizationController {
    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private CodeGenerator codeGenerator;

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

    @PostMapping("/organizations")
    @Secured("ROLE_DSMHACK_ADMINISTRATOR")
    public ResponseEntity<Organization> save(@Validated @RequestBody Organization organization) {
        organization.setOrgGuid(codeGenerator.generateUUID());
        return new ResponseEntity<>(organizationRepository.save(organization), HttpStatus.CREATED);
    }

}