package org.dsmhack.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.dsmhack.model.LoginToken;
import org.dsmhack.model.User;
import org.dsmhack.repository.LoginTokenRepository;
import org.dsmhack.repository.UserRepository;
import org.dsmhack.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class LoginController {
    private static final long THIRTY_MINUTES = 750_000;
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String HEADER_STRING = "Authorization";
    @Value("${sendgrid.api.key}")
    private String sendGridApiKey;

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

        if (user != null) {
            String token = Jwts.builder()
                    .setSubject(((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername())
                    .setExpiration(new Date(System.currentTimeMillis() + THIRTY_MINUTES))
                    .signWith(SignatureAlgorithm.HS512, jwtEncryptionKey.getBytes())
                    .compact();
            response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
            response.addHeader("access-control-expose-headers", "Authorization");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("access-control-expose-headers", "Authorization");
        return new ResponseEntity<>(user, headers, HttpStatus.OK);
    }
}