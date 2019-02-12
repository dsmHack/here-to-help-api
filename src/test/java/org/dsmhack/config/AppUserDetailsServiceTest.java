package org.dsmhack.config;

import org.dsmhack.model.LoginToken;
import org.dsmhack.repository.LoginTokenRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;

import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AppUserDetailsServiceTest {

  @InjectMocks
  private AppUserDetailsService appUserDetailsService;

  @Mock
  private LoginTokenRepository loginTokenRepository;

  @Test
  public void setsCredentialsExpiredWhenTokenExpirationBeforeTimeNow() throws Exception {
    LoginToken loginToken = new LoginToken();
    loginToken.setToken("token");
    loginToken.setUserGuid("guid");
    loginToken.setTokenExpDate(LocalDateTime.MIN);
    when(loginTokenRepository.findByUserGuid(anyString())).thenReturn(loginToken);

    UserDetails userDetails = appUserDetailsService.loadUserByUsername("any");

    assertFalse(userDetails.isCredentialsNonExpired());
  }
}