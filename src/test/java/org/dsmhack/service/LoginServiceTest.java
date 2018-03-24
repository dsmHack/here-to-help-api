package org.dsmhack.service;

import org.dsmhack.model.LoginToken;
import org.dsmhack.model.User;
import org.dsmhack.repository.EmailSender;
import org.dsmhack.repository.LoginTokenRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginServiceTest {

    @InjectMocks
    private LoginService loginService;

    @Mock
    private CodeGenerator codeGenerator;

    @Mock
    private EmailSender emailSender;

    @Mock
    private LoginTokenRepository loginTokenRepository;

    @Test
    public void loginCallsGuidServiceToForCode() throws Exception {
        loginService.login(new User());
        verify(codeGenerator).generateLoginToken();
    }

    @Test
    public void loginPassesGeneratedCodeToEmailSender() throws Exception {
        when(codeGenerator.generateLoginToken()).thenReturn("code");
        User user = new User();
        user.setEmail("a@aol.com");
        loginService.login(user);
        verify(emailSender).sendTo("a@aol.com", "code");
    }

    @Test
    public void loginSavesTokenToRepository() throws Exception {
        loginService.login(new User());
        verify(loginTokenRepository).save(any(LoginToken.class));
    }

    @Test
    public void loginSavesTokenToRepositoryWithCorrectFields() throws Exception {
        when(codeGenerator.generateLoginToken()).thenReturn("123");
        User user = new User();
        user.setUserGuid("userGuid");
        loginService.login(user);
        ArgumentCaptor<LoginToken> captor = ArgumentCaptor.forClass(LoginToken.class);
        verify(loginTokenRepository).save(captor.capture());
        assertEquals("123", captor.getValue().getToken());
        assertEquals("userGuid", captor.getValue().getUserGuid());
    }
}