package org.dsmhack.controller;

import org.dsmhack.model.LoginToken;
import org.dsmhack.model.User;
import org.dsmhack.repository.LoginTokenRepository;
import org.dsmhack.repository.UserRepository;
import org.dsmhack.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoginTokenRepository loginTokenRepository;

    @PostMapping("/login/sendCode")
    public ResponseEntity login(@RequestBody String emailAddress) {
        User user = userRepository.findByEmail(emailAddress);
        loginService.login(user);
        return new ResponseEntity(HttpStatus.CREATED);
    }
    
    @PostMapping("/login")
    public ResponseEntity<User> verifyCode(@RequestBody String securityToken) {
        LoginToken loginToken = loginTokenRepository.findByToken(securityToken);
        User user = userRepository.findOne(loginToken.getUserGuid());
        HttpHeaders headers = new HttpHeaders();
        headers.add("access-control-expose-headers", "Authorization");
        return new ResponseEntity<>(user, headers, HttpStatus.OK);
    }
}