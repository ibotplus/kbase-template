package com.eastrobot.kbs.template.auth;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class KbsUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO add by Yogurt_lei at 2019-03-18 15:02: 实现具体的dao查询逻辑
        if ("admin".equals(username)) {
            return new User(username, "admin", AuthorityUtils.createAuthorityList("ROLE_USER"));
        }

        throw new UsernameNotFoundException(AuthenticationConstants.MSG_ACCOUNT_NOT_FOUND);
    }
}