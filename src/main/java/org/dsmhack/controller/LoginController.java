package org.dsmhack.controller;

import org.dsmhack.model.User;
import org.dsmhack.repository.UserRepository;
import org.dsmhack.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login/sendCode")
    public ResponseEntity login(@RequestBody String emailAddress) {
        User user = userRepository.findByEmail(emailAddress);
        loginService.login(user);
        return new ResponseEntity(HttpStatus.OK);
    }
    
    @PostMapping("/login/verifyCode")
    public ResponseEntity verifyCode(@RequestBody String securityToken) throws Exception {
        return new ResponseEntity(HttpStatus.OK);
    }
}