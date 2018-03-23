package org.dsmhack.controller;

import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OrganizationControllerTest {

    private MockMvc mockMvc;

    @Test
    public void defaultMappingReturns200() throws Exception {
        OrganizationController organizationController = new OrganizationController();
        mockMvc = MockMvcBuilders.standaloneSetup(organizationController).build();
        mockMvc.perform(get("/organizations"))
                .andExpect(status().isOk());
    }

    @Test
    public void getOrgByIdReturns200() throws Exception {
        OrganizationController organizationController = new OrganizationController();
        mockMvc = MockMvcBuilders.standaloneSetup(organizationController).build();
        mockMvc.perform(get("/organizations/1"))
                .andExpect(status().isOk());
    }
}