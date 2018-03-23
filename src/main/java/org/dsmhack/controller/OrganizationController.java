package org.dsmhack.controller;

import org.dsmhack.model.Organization;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
public class OrganizationController {

    @GetMapping("/organizations")
    public List<Organization> getAllOrganizations(){
        Organization fakeOrg = createFakeOrg("Org 1");
        Organization fakeOrg2 = createFakeOrg("Org 2");
        return Arrays.asList(fakeOrg, fakeOrg2);
    }

    @GetMapping("/organizations/{organizationId}")
    public Organization getOrganizationBy(@PathVariable String organizationId){
        return createFakeOrg("Organization with id of " + organizationId);
    }

    private Organization createFakeOrg(String name) {
        Organization organization = new Organization();
        organization.setOrganizationId(new UUID(5L, 2L));
        organization.setName(name);
        organization.setDescription("Org description");
        organization.setEmail("joeCool@aol.com");
        organization.setFacebookUrl("/orgFacebookUrl");
        organization.setInstagramUrl("/gram");
        organization.setPhoneNumber("515-555-2800");
        organization.setTwitterUrl("/twitter");
        organization.setWebsiteUrl("www.dsmhack.org");
        return null;
    }
}
