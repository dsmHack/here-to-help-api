package org.dsmhack.service;

import org.dsmhack.repository.EmailSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

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

    @Test
    public void loginCallsGuidServiceToForCode() throws Exception {
        loginService.login("a@aol.com");
        verify(codeGenerator).generateLoginToken();
    }

    @Test
    public void loginPassesGeneratedCodeToEmailSender() throws Exception {
        when(codeGenerator.generateLoginToken()).thenReturn("code");
        loginService.login("a@aol.com");
        verify(emailSender).sendTo("a@aol.com", "code");
    }
}