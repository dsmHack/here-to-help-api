package org.dsmhack.controller;

import org.dsmhack.model.CheckIn;
import org.dsmhack.model.Project;
import org.dsmhack.repository.CheckInRepository;
import org.dsmhack.repository.ProjectRepository;
import org.dsmhack.service.CodeGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class ProjectControllerTest {

    private MockMvc mockMvc;
    @InjectMocks
    private ProjectController projectController;
    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private CodeGenerator codeGenerator;
    @Mock
    private CheckInRepository checkInRepository;

    @Test
    public void getAllProjectsReturns200() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(projectController).build();
        mockMvc.perform(get("/projects"))
                .andExpect(status().isOk());
    }

    @Test
    public void getProjectByIdReturns200() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(projectController).build();
        mockMvc.perform(get("/projects/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void postCallsRepositoryToSaveProject() throws Exception {
        Project project = new Project();
        projectController.save(project);
        verify(projectRepository).save(project);
    }

    @Test
    public void postCallsGuidGeneratorToGenerateUUIDBeforeSavingProject() throws Exception {
        String projectId = "randomUUID";
        when(codeGenerator.generateUUID()).thenReturn(projectId);
        projectController.save(new Project());
        ArgumentCaptor<Project> captor = ArgumentCaptor.forClass(Project.class);
        verify(projectRepository).save(captor.capture());
        assertEquals(projectId, captor.getValue().getProjGuid());
    }

    @Test
    public void postReturnsSavedProject() throws Exception {
        Project expectedProject = new Project();
        when(projectRepository.save(any(Project.class))).thenReturn(expectedProject);
        Project actualProject = projectController.save(new Project());
        assertEquals(expectedProject, actualProject);
    }

    @Test
    public void getAllProjectsCallsRepository() throws Exception {
        List<Project> projects = Arrays.asList(new Project(), new Project());
        when(projectRepository.findAll()).thenReturn(projects);
        assertEquals(projects, projectController.getAllProjects());
    }

    @Test
    public void getProjectsByIdCallsRepositoryFind() throws Exception {
        Project project = new Project();
        when(projectRepository.findOne(anyString())).thenReturn(project);
        assertEquals(project, projectController.getProjectById(""));
    }

    @Test
    public void findAllCheckinsReturns200() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(projectController).build();
        mockMvc.perform(get("/projects/1/check-ins"))
                .andExpect(status().isOk());

    }

    @Test
    public void findAllCheckinsRetrievesCheckingsFromRepository() throws Exception {
        List<CheckIn> expectedCheckins = Collections.singletonList(new CheckIn());
        when(checkInRepository.findByProjGuid("guid")).thenReturn(expectedCheckins);
        assertEquals(expectedCheckins, projectController.findAllCheckins("guid"));
    }
}