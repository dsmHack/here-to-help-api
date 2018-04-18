package org.dsmhack.controller;

import com.google.gson.Gson;
import org.dsmhack.model.User;
import org.dsmhack.repository.UserRepository;
import org.dsmhack.service.CodeGenerator;
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

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserControllerTest {

    private MockMvc mockMvc;
    @InjectMocks
    private UserController userController;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CodeGenerator codeGenerator;

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
    public void postUserByIdReturns400_notNull() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        MvcResult mvcResult = mockMvc.perform(
            post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}")
        ).andExpect(
            status().isBadRequest()
        ).andReturn();

        String message = mvcResult.getResolvedException().getMessage();
        assertTrue(message.contains("NotNull.user.firstName"));
        assertTrue(message.contains("NotNull.user.lastName"));
        assertTrue(message.contains("NotNull.user.email"));
        assertTrue(message.contains("NotNull.user.role"));
    }

    @Test
    public void postUserByIdReturns201_volunteer() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        MvcResult mvcResult = mockMvc.perform(
            post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new User()
                    .setFirstName("John")
                    .setLastName("Doe")
                    .setEmail("jdoe@example.com")
                    .setRole("Volunteer")
                    .toJson())
        ).andExpect(
            status().isCreated()
        ).andReturn();

        assertEquals(null, mvcResult.getResolvedException());
    }

    @Test
    public void postUserByIdReturns201_organizationAdministrator() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        MvcResult mvcResult = mockMvc.perform(
            post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new User()
                    .setFirstName("John")
                    .setLastName("Doe")
                    .setEmail("jdoe@example.com")
                    .setRole("Organization Administrator")
                    .toJson())
        ).andExpect(
            status().isCreated()
        ).andReturn();

        assertEquals(null, mvcResult.getResolvedException());
    }

    @Test
    public void postUserByIdReturns201_dsmHackAdministrator() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        MvcResult mvcResult = mockMvc.perform(
            post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new User()
                    .setFirstName("John")
                    .setLastName("Doe")
                    .setEmail("jdoe@example.com")
                    .setRole("dsmHack Administrator")
                    .toJson())
        ).andExpect(
            status().isCreated()
        ).andReturn();

        assertEquals(null, mvcResult.getResolvedException());
    }

    @Test
    public void postUserByIdReturns400_size() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        MvcResult mvcResult = mockMvc.perform(
            post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new User()
                        .setFirstName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                        .setLastName("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb")
                        .setEmail("cccccccccccccccccccccccccccccccccccccccc@example.com")
                        .setRole("ddddddddddddddddddddddddddddddddddddddddddddddddddd")
                        .toJson())
        ).andExpect(
            status().isBadRequest()
        ).andReturn();

        String message = mvcResult.getResolvedException().getMessage();
        assertTrue(message.contains("Size.user.firstName"));
        assertTrue(message.contains("Size.user.lastName"));
        assertTrue(message.contains("Size.user.email"));
        assertTrue(message.contains("Size.user.role"));
    }

    @Test
    public void postUserByIdReturns400_email() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        MvcResult mvcResult = mockMvc.perform(
            post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new User()
                    .setFirstName("John")
                    .setLastName("Doe")
                    .setEmail("BadEmail")
                    .setRole("admin")
                    .toJson())
        ).andExpect(
            status().isBadRequest()
        ).andReturn();

        assertTrue(mvcResult.getResolvedException().getMessage().contains("Email.user.email"));
    }

    @Test
    public void postUserByIdReturns400_roleVolunteer() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        MvcResult mvcResult = mockMvc.perform(
            post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new User()
                    .setFirstName("firstName")
                    .setLastName("lastName")
                    .setEmail("email@example.com")
                    .setRole("someInvalidRole")
                    .toJson())
        ).andExpect(
                status().isBadRequest()
        ).andReturn();

        assertTrue(mvcResult.getResolvedException().getMessage().contains("Pattern.user.role"));
    }

    @Test
    public void postCallsGuidGeneratorToGenerateUUIDBeforeSavingUser() throws Exception {
        UUID userId = UUID.randomUUID();
        when(codeGenerator.generateUUID()).thenReturn(userId);
        userController.save(new User());
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        assertEquals(userId, captor.getValue().getUserGuid());
    }

    @Test
    public void postReturnsSavedUser() throws Exception {
        User expectedUser = new User();
        when(userRepository.save(any(User.class))).thenReturn(expectedUser);
        User actualUser = userController.save(new User()).getBody();
        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void getAllUsersReturnsUsersFromRepository() throws Exception {
        List<User> users = Arrays.asList(new User(), new User());
        when(userRepository.findAll()).thenReturn(users);
        assertEquals(users, userController.getAllUsers());
    }

    @Test
    public void getUserByIdReturnsUserFromRepository() throws Exception {
        User user = new User();
        when(userRepository.findOne(anyString())).thenReturn(user);
        assertEquals(user, userController.getUserById(""));
    }
}
