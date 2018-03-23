package org.dsmhack.controller;

import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class HelloControllerTest {

    private MockMvc mockMvc;

    @Test
    public void defaultMappingReturns200() throws Exception {
        HelloController helloController = new HelloController();
        mockMvc = MockMvcBuilders.standaloneSetup(helloController).build();
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }
}