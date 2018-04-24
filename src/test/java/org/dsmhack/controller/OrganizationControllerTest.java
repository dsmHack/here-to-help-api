package org.dsmhack.controller;

import org.dsmhack.model.Organization;
import org.dsmhack.model.Project;
import org.dsmhack.repository.OrganizationRepository;
import org.dsmhack.repository.ProjectRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class OrganizationControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private OrganizationController organizationController;

    @Mock
    private OrganizationRepository organizationRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Test
    public void defaultMappingReturns200() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(organizationController).build();
        mockMvc.perform(get("/organizations"))
                .andExpect(status().isOk());
    }

    @Test
    public void getOrgByIdReturns200() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(organizationController).build();
        mockMvc.perform(get("/organizations/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void getOrganizationCallsRepositoryFindAll() throws Exception {
        organizationController.getAllOrganizations();
        verify(organizationRepository).findAll();
    }

    @Test
    public void getOrganizationReturnsListOfOrganizations() throws Exception {
        List<Organization> expoectedReposonse = Arrays.asList(new Organization(), new Organization());
        when(organizationRepository.findAll()).thenReturn(expoectedReposonse);
        List<Organization> allOrganizations = organizationController.getAllOrganizations();
        assertEquals(expoectedReposonse, allOrganizations);
    }

    @Test
    public void getOrganizationByReturnsOrganization() throws Exception {
        Organization organization = new Organization();
        when(organizationRepository.findOne(anyString())).thenReturn(organization);
        assertEquals(organization, organizationController.getOrganizationBy(""));
    }

    @Test
    public void findProjectsForOrganizationReturns200() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(organizationController).build();
        mockMvc.perform(get("/organizations/12341235135/projects"))
                .andExpect(status().isOk());
    }

    @Test
    public void findProjectsReturnsProjectsFromRepository() throws Exception {
        List<Project> expectedProjects = Arrays.asList(new Project(), new Project());
        when(projectRepository.findByOrganizationGuid("orgUUID")).thenReturn(expectedProjects);
        assertEquals(expectedProjects, organizationController.findProjectsForOrganization("orgUUID"));
    }
}