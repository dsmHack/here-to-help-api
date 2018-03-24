package org.dsmhack.service;

import org.dsmhack.model.LoginToken;
import org.dsmhack.model.User;
import org.dsmhack.repository.EmailSender;
import org.dsmhack.repository.LoginTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.concurrent.TimeUnit;

@Service
public class LoginService {

    @Autowired
    private CodeGenerator codeGenerator;

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private LoginTokenRepository loginTokenRepository;

    private String hostUrlToChange = "localhost:4200/login-confirm/";

    public void login(User user) {
        String token = hostUrlToChange + codeGenerator.generateUUID();
        LoginToken loginToken = new LoginToken();
        loginToken.setToken(token);
        loginToken.setUserGuid(user.getUserGuid());
        loginToken.setTokenExpDate(twentyMinutesFromNow());
        loginTokenRepository.save(loginToken);
        emailSender.sendTo(user.getEmail(), token);
    }

    private Timestamp twentyMinutesFromNow() {
        LocalDateTime tokenExpiration = LocalDateTime.now().plus(20, ChronoUnit.MINUTES);
        return Timestamp.valueOf(tokenExpiration);
    }
}