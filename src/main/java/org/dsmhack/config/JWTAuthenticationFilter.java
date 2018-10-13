package org.dsmhack.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.dsmhack.model.LoginToken;
import org.dsmhack.repository.LoginTokenRepository;
import org.dsmhack.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private static final long THIRTY_MINUTES = 750_000;
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String HEADER_STRING = "Authorization";

    private final String jwtEncryptionKey;
    private final AuthenticationManager authenticationManager;
    private final LoginTokenRepository loginTokenRepository;
    private final UserRepository userRepository;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, String jwtEncryptionKey, ApplicationContext ctx) {
        this.authenticationManager = authenticationManager;
        this.jwtEncryptionKey = jwtEncryptionKey;
        this.loginTokenRepository = ctx.getBean(LoginTokenRepository.class);
        this.userRepository = ctx.getBean(UserRepository.class);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        try {
            LoginToken credentials = new ObjectMapper().readValue(req.getInputStream(), LoginToken.class);
            LoginToken loginToken = this.loginTokenRepository.findByToken(credentials.getToken());
            String userGuid = loginToken.getUserGuid();
            List<GrantedAuthority> roles = Collections.singletonList(new SimpleGrantedAuthority(userRepository.findOne(userGuid).getRole()));
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userGuid, loginToken.getToken(), roles);
            return authenticationManager.authenticate(authentication);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        String token = Jwts.builder()
                .setSubject(((User) auth.getPrincipal()).getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + THIRTY_MINUTES))
                .signWith(SignatureAlgorithm.HS512, jwtEncryptionKey.getBytes())
                .compact();
        response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
    }
}