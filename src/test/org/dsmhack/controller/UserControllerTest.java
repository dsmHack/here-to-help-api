package org.dsmhack.controller;

import org.dsmhack.model.User;
import org.dsmhack.repository.UserRepository;
import org.dsmhack.service.GuidGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserControllerTest {

    private MockMvc mockMvc;
    @InjectMocks
    private UserController userController;
    @Mock
    private UserRepository userRepository;
    @Mock
    private GuidGenerator guidGenerator;

    @Test
    public void getUserByIdReturns200() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void postCallsRepositoryToSaveUser() throws Exception {
        User user = new User();
        userController.save(user);
        verify(userRepository).save(user);
    }

    @Test
    public void postCallsGuidGeneratorToGenerateUUIDBeforeSavingUser() throws Exception {
        UUID userId = UUID.randomUUID();
        when(guidGenerator.generate()).thenReturn(userId);
        userController.save(new User());
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        assertEquals(userId, captor.getValue().getUserId());
    }

    @Test
    public void postReturnsSavedUser() throws Exception {
        User expectedUser = new User();
        when(userRepository.save(any(User.class))).thenReturn(expectedUser);
        User actualUser = userController.save(new User());
        assertEquals(expectedUser, actualUser);
    }
}