package org.dsmhack.controller;

import org.dsmhack.model.LoginToken;
import org.dsmhack.model.User;
import org.dsmhack.repository.LoginTokenRepository;
import org.dsmhack.repository.UserRepository;
import org.dsmhack.service.LoginService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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

    @Mock
    private LoginTokenRepository loginTokenRepository;

    @Before
    public void setup() {
        ReflectionTestUtils.setField(loginController, "jwtSecret", "someSecret");
        when(userRepository.findByEmail(anyString())).thenReturn(new User());
        mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
    }

    @Test
    public void loginSendCodeReturns201() throws Exception {
        mockMvc.perform(post("/login/sendCode")
                .contentType(MediaType.APPLICATION_JSON)
                .content("email address"))
                .andExpect(status().isCreated());
    }

    @Test
    public void loginSendCodeCallsLoginServiceWithEmailAddress() throws Exception {
        loginController.login("test@aol.com");
        verify(loginService).login(any(User.class));
    }

    @Test
    public void loginReturns200() throws Exception {
        String securityToken = "securityToken";
        String userGuid = "userGuid";
        User user = new User();
        LoginToken loginToken = new LoginToken()
                .setUserGuid(userGuid);
        when(loginTokenRepository.findByToken(securityToken)).thenReturn(loginToken);
        when(userRepository.findOne(userGuid)).thenReturn(user);

        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(securityToken))
                .andExpect(status().isOk());
    }

    @Test
    public void loginReturns403() throws Exception {
        String securityToken = "securityToken";
        when(loginTokenRepository.findByToken(securityToken)).thenReturn(null);

        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(securityToken))
                .andExpect(status().isForbidden());
    }

    @Test
    public void loginCallsLoginTokenRepositoryWithSecurityTokenAndReturnsUser() throws Exception {
        String securityToken = "securityToken";
        String userGuid = "userGuid";
        User expectedUser = new User();
        LoginToken loginToken = new LoginToken()
                .setUserGuid(userGuid);
        when(loginTokenRepository.findByToken(securityToken)).thenReturn(loginToken);
        when(userRepository.findOne(userGuid)).thenReturn(expectedUser);

        ResponseEntity responseEntity = loginController.verifyCode(securityToken);

        assertEquals(expectedUser, responseEntity.getBody());
        assertEquals("Authorization", responseEntity.getHeaders().getAccessControlExposeHeaders().get(0));
        assertTrue(responseEntity.getHeaders().get("Authorization").get(0).startsWith("Bearer "));
        verify(loginTokenRepository).findByToken(securityToken);
        verify(userRepository).findOne(userGuid);
    }
}