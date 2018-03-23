package org.dsmhack.controller;

import org.dsmhack.model.Organization;
import org.dsmhack.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrganizationController {

    @Autowired
    private OrganizationRepository organizationRepository;

    @GetMapping("/organizations")
    public List<Organization> getAllOrganizations() {
        return organizationRepository.findAll();
    }

    @GetMapping("/organizations/{organizationId}")
    public Organization getOrganizationBy(@PathVariable String organizationId) {
        return organizationRepository.findOne(organizationId);
    }
}