package org.dsmhack.controller;

import org.dsmhack.model.Organization;
import org.dsmhack.model.Project;
import org.dsmhack.repository.OrganizationRepository;
import org.dsmhack.repository.ProjectRepository;
import org.dsmhack.service.CodeGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @Mock
    private CodeGenerator codeGenerator;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(organizationController).build();
    }

    @Test
    public void defaultMappingReturns200() throws Exception {
        mockMvc.perform(get("/organizations"))
                .andExpect(status().isOk());
    }

    @Test
    public void getOrgByIdReturns200() throws Exception {
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
        List<Organization> expectedResponse = Arrays.asList(new Organization(), new Organization());
        when(organizationRepository.findAll()).thenReturn(expectedResponse);
        List<Organization> allOrganizations = organizationController.getAllOrganizations();
        assertEquals(expectedResponse, allOrganizations);
    }

    @Test
    public void getOrganizationByReturnsOrganization() throws Exception {
        Organization organization = new Organization();
        when(organizationRepository.findOne(anyString())).thenReturn(organization);
        assertEquals(organization, organizationController.getOrganizationBy(""));
    }

    @Test
    public void findProjectsForOrganizationReturns200() throws Exception {
        mockMvc.perform(get("/organizations/12341235135/projects"))
                .andExpect(status().isOk());
    }

    @Test
    public void findProjectsReturnsProjectsFromRepository() throws Exception {
        List<Project> expectedProjects = Arrays.asList(new Project(), new Project());
        when(projectRepository.findByOrgGuid("orgUUID")).thenReturn(expectedProjects);
        assertEquals(expectedProjects, organizationController.findProjectsForOrganization("orgUUID"));
    }

    @Test
    public void postOrganizationByIdReturns201() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
            post("/organizations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Organization()
                    .setName("name")
                    .setDescription("description")
                    .setWebsiteUrl("websiteUrl")
                    .toJson())
        ).andExpect(
            status().isCreated()
        ).andReturn();

        assertEquals(null, mvcResult.getResolvedException());
    }

    @Test
    public void postUserByIdReturns400_notNull() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
            post("/organizations")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}")
        ).andExpect(
            status().isBadRequest()
        ).andReturn();

        String message = mvcResult.getResolvedException().getMessage();
        assertTrue(message.contains("NotNull.organization.name"));
        assertTrue(message.contains("NotNull.organization.description"));
        assertTrue(message.contains("NotNull.organization.websiteUrl"));
    }

    @Test
    public void postUserByIdReturns400_size() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
            post("/organizations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Organization()
                    .setName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                    .setDescription("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb")
                    .setWebsiteUrl("ccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc")
                    .toJson())
        ).andExpect(
            status().isBadRequest()
        ).andReturn();

        String message = mvcResult.getResolvedException().getMessage();
        assertTrue(message.contains("Size.organization.name"));
        assertTrue(message.contains("Size.organization.description"));
        assertTrue(message.contains("Size.organization.websiteUrl"));
    }

    @Test
    public void postReturnsSavedProject() throws Exception {
        Organization expectedOrganization = new Organization();
        when(organizationRepository.save(any(Organization.class))).thenReturn(expectedOrganization);
        Organization actualOrganization = organizationController.save(new Organization()).getBody();
        assertEquals(expectedOrganization, actualOrganization);
    }
}