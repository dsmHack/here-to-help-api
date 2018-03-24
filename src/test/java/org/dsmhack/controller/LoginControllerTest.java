package org.dsmhack.controller;

import org.dsmhack.service.LoginService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class LoginControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private LoginController loginController;

    @Mock
    private LoginService loginService;

    @Test
    public void loginReturns200() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("email address"))
                .andExpect(status().isOk());
    }

    @Test
    public void loginCallsLoginServiceWithEmailAddress() throws Exception {
        loginController.login("test@aol.com");
        verify(loginService).login("test@aol.com");
    }
}