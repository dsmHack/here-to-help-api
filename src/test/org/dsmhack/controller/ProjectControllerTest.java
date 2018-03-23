package org.dsmhack.controller;

import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProjectControllerTest {

    private MockMvc mockMvc;

    @Test
    public void getAllProjectsReturns200() throws Exception {
        ProjectController projectController = new ProjectController();
        mockMvc = MockMvcBuilders.standaloneSetup(projectController).build();
        mockMvc.perform(get("/projects"))
                .andExpect(status().isOk());
    }

    @Test
    public void getProjectByIdReturns200() throws Exception {
        ProjectController projectController = new ProjectController();
        mockMvc = MockMvcBuilders.standaloneSetup(projectController).build();
        mockMvc.perform(get("/projects/1"))
                .andExpect(status().isOk());
    }
}