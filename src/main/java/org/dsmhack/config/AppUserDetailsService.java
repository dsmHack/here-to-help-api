package org.dsmhack.config;

import org.dsmhack.model.LoginToken;
import org.dsmhack.repository.LoginTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;

@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private LoginTokenRepository loginTokenRepository;

    @Override
    public UserDetails loadUserByUsername(String userGuid) throws UsernameNotFoundException {
        LoginToken securityCode = loginTokenRepository.findByUserGuid(userGuid);
        boolean credentialsNonExpired = LocalDateTime.now().isBefore(securityCode.getTokenExpDate());
        if (securityCode == null) {
            throw new UsernameNotFoundException(userGuid);
        }
        return new User(userGuid, securityCode.getToken(), true, true, credentialsNonExpired, true, Collections.emptyList());
    }
}