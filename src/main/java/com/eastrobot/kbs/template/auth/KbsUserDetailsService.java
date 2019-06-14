package com.eastrobot.kbs.template.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class KbsUserDetailsService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO add by Yogurt_lei at 2019-03-18 15:02: 实现具体的dao查询逻辑
        // // TODO by Yogurt_lei at 2019-06-13 16:16 : 冗余代码, 这是测试方便登录, 前端密码需要使用md5编码
        String admin = passwordEncoder.encode("admin");

        if ("admin".equals(username)) {
            return new User(username, admin, AuthorityUtils.createAuthorityList("ROLE_USER"));
        }

        throw new UsernameNotFoundException("User Not Found");
    }
}