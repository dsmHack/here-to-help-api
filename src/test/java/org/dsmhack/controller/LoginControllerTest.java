package org.dsmhack.controller;

import org.dsmhack.model.User;
import org.dsmhack.repository.UserRepository;
import org.dsmhack.service.LoginService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class LoginControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private LoginController loginController;

    @Mock
    private LoginService loginService;

    @Mock
    private UserRepository userRepository;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
    }

    @Test
    public void loginReturns201() throws Exception {
        when(userRepository.findByEmail(anyString())).thenReturn(new User());

        mockMvc.perform(post("/login/sendCode")
                .contentType(MediaType.APPLICATION_JSON)
                .content("test@aol.com"))
                .andExpect(status().isCreated());
    }

    @Test
    public void loginCallsLoginServiceWithEmailAddress() throws Exception {
        when(userRepository.findByEmail(anyString())).thenReturn(new User());

        loginController.login("test@aol.com");
        verify(loginService).login(any(User.class));
    }

    @Test
    public void emailDoesNotExist() throws Exception {
        when(userRepository.findByEmail(anyString())).thenReturn(null);

        MvcResult mvcResult = mockMvc.perform(
                post("/login/sendCode")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("test@aol.com")
        ).andExpect(
                status().isNotFound()
        ).andReturn();
    }
}