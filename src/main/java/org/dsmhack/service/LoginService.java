package org.dsmhack.service;

import org.dsmhack.repository.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private CodeGenerator codeGenerator;

    @Autowired
    private EmailSender emailSender;

    public void login(String emailAddress) {
        String loginToken = codeGenerator.generateLoginToken();
        emailSender.sendTo(emailAddress, loginToken);
    }
}