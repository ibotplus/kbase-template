package com.eastrobot.kbs.template.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * 具体认证方法提供者
 *
 * @author Yogurt_lei
 * @date 2019-03-15 9:26
 */
@Component
public class KbsAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private KbsUserDetailsService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String credentials = authentication.getCredentials().toString();
        String username = authentication.getName();


        UserDetails userDetails = userService.loadUserByUsername(username);
        if (!userDetails.getPassword().equals(credentials)) {
            throw new BadCredentialsException(AuthenticationConstants.MSG_MISMATCHING_CREDENTIALS);
        }

        return new UsernamePasswordAuthenticationToken(userDetails, authentication.getCredentials(),
                userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

}