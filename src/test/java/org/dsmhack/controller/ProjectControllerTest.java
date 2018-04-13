package org.dsmhack.controller;

import org.dsmhack.model.CheckIn;
import org.dsmhack.model.Project;
import org.dsmhack.repository.CheckInRepository;
import org.dsmhack.repository.ProjectRepository;
import org.dsmhack.service.CodeGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

    @Before
    public void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(projectController).build();
    }

    @Test
    public void getAllProjectsReturns200() throws Exception {
        mockMvc.perform(get("/projects"))
                .andExpect(status().isOk());
    }

    @Test
    public void getProjectByIdReturns200() throws Exception {
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
        mockMvc.perform(get("/projects/1/check-ins"))
                .andExpect(status().isOk());

    }

    @Test
    public void findAllCheckinsRetrievesCheckingsFromRepository() throws Exception {
        List<CheckIn> expectedCheckins = Collections.singletonList(new CheckIn());
        when(checkInRepository.findByProjGuid("guid")).thenReturn(expectedCheckins);
        assertEquals(expectedCheckins, projectController.findAllCheckins("guid"));
    }

    //todo: modify this to return a 201 rather than a 200
    @Test
    public void postProjectByIdReturns200() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
            post("/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Project()
                    .setOrgGuid("orgGuid")
                    .setName("name")
                    .setDescription("description")
                    .toJson())
        ).andExpect(
            status().isOk()
        ).andReturn();

        assertEquals(null, mvcResult.getResolvedException());
    }

    @Test
    public void postUserByIdReturns400_notNull() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
            post("/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}")
        ).andExpect(
            status().isBadRequest()
        ).andReturn();

        String message = mvcResult.getResolvedException().getMessage();
        assertTrue(message.contains("Organization guid is required."));
        assertTrue(message.contains("Name is required."));
        assertTrue(message.contains("Description is required."));
    }

    @Test
    public void postUserByIdReturns400_size() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
            post("/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Project()
                    .setOrgGuid("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                    .setName("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb")
                    .setDescription("ccccccccccccccccccccccccccccccccccccccccccccccccccc")
                    .toJson())
        ).andExpect(
            status().isBadRequest()
        ).andReturn();

        String message = mvcResult.getResolvedException().getMessage();
        assertTrue(message.contains("Organization guid must be between 1 and 36 characters."));
        assertTrue(message.contains("Name must be between 1 and 50 characters."));
        assertTrue(message.contains("Description must be between 1 and 50 characters."));
    }

    @Test
    public void checkinReturns200() throws Exception {
        mockMvc.perform(post("/projects/12345/check-ins")
                .contentType(MediaType.APPLICATION_JSON)
                .content("user-uuid"))
                .andExpect(status().isOk());
    }

    @Test
    public void postToCheckinCallsRepositoryWithCheckIn() throws Exception {
        projectController.checkUserIn("12345", "userId");
        ArgumentCaptor<CheckIn> captor = ArgumentCaptor.forClass(CheckIn.class);
        verify(checkInRepository).save(captor.capture());
        assertEquals("userId", captor.getValue().getUserGuid());
        assertEquals("12345", captor.getValue().getProjGuid());
    }

    @Test
    public void checkInReturnsCheckIn() throws Exception {
        CheckIn expectedCheckin = new CheckIn();
        when(checkInRepository.save(any(CheckIn.class))).thenReturn(expectedCheckin);
        assertEquals(expectedCheckin, projectController.checkUserIn("12345", "userId"));
    }

    @Test
    public void checkoutReturns200() throws Exception {
        when(checkInRepository.findByProjGuidAndUserGuid(anyString(), anyString())).thenReturn(new CheckIn());
        mockMvc.perform(put("/projects/12345/check-ins")
                .contentType(MediaType.APPLICATION_JSON)
                .content("user-uuid"))
                .andExpect(status().isOk());    }

    @Test
    public void checkoutRetrievesCheckinFromRepository() throws Exception {
        when(checkInRepository.findByProjGuidAndUserGuid(anyString(), anyString())).thenReturn(new CheckIn());
        projectController.checkOutUser("projectGuid", "userGuid");
        verify(checkInRepository).findByProjGuidAndUserGuid("projectGuid", "userGuid");
    }

    @Test
    public void checkOutSavesCheckIn() throws Exception {
        CheckIn checkIn = new CheckIn();
        when(checkInRepository.findByProjGuidAndUserGuid(anyString(), anyString())).thenReturn(checkIn);
        projectController.checkOutUser("projectGuid", "userGuid");
        verify(checkInRepository).save(checkIn);
    }

    @Test
    public void checkOutReturnsCheckIn() throws Exception {
        CheckIn expectedCheckin = new CheckIn();
        when(checkInRepository.save(any(CheckIn.class))).thenReturn(expectedCheckin);
        when(checkInRepository.findByProjGuidAndUserGuid(anyString(), anyString())).thenReturn(expectedCheckin);
        assertEquals(expectedCheckin, projectController.checkOutUser("projectGuid", "userGuid") );
    }
}