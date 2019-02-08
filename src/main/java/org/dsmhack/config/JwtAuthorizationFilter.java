package org.dsmhack.config;

import io.jsonwebtoken.Jwts;
import org.dsmhack.repository.UserRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
  private static final String TOKEN_PREFIX = "Bearer ";
  private static final String HEADER_STRING = "Authorization";

  private final String jwtEncryptionKey;
  private final UserRepository userRepository;

  public JwtAuthorizationFilter(
      AuthenticationManager authManager, String jwtEncryptionKey, ApplicationContext ctx) {
    super(authManager);
    this.jwtEncryptionKey = jwtEncryptionKey;
    this.userRepository = ctx.getBean(UserRepository.class);
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
          throws IOException, ServletException {
    final String header = request.getHeader(HEADER_STRING);

    final boolean hasBearer = header != null && header.startsWith(TOKEN_PREFIX);
    if (hasBearer) {
      UsernamePasswordAuthenticationToken authentication = getAuthentication(request);

      SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    chain.doFilter(request, response);
  }

  private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
    String token = request.getHeader(HEADER_STRING);
    if (token != null) {
      String user = Jwts.parser()
              .setSigningKey(jwtEncryptionKey.getBytes())
              .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
              .getBody()
              .getSubject();

      if (user != null) {
        List<GrantedAuthority> roles = Collections.singletonList(
            new SimpleGrantedAuthority(userRepository.findOne(user).getRole()));
        return new UsernamePasswordAuthenticationToken(user, null, roles);
      }
      return null;
    }
    return null;
  }
}