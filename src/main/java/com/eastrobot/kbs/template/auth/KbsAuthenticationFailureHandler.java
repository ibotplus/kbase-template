package com.eastrobot.kbs.template.auth;

import com.eastrobot.kbs.template.exception.ResponseEntity;
import com.eastrobot.kbs.template.exception.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理校验失败的handler
 */
@Slf4j
public class KbsAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {

        ResultCode resultCode = ResultCode.AUTH_UNKNOWN_AUTHENTICATE;
        if (exception instanceof SessionAuthenticationException) {
            // session 限制, 用户重复登录
            resultCode = ResultCode.AUTH_SESSION_AUTHENTICATION;
        } else if (exception instanceof UsernameNotFoundException) {
            // 用户不存在
            resultCode = ResultCode.AUTH_USERNAME_NOT_FOUND;
        } else if (exception instanceof BadCredentialsException) {
            // 密码错误
            resultCode = ResultCode.AUTH_BAD_CREDENTIALS;
        }

        log.error("AuthenticationFailureHandler: {}", exception.getMessage());

        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().print(ResponseEntity.ofFailure(resultCode, exception.getMessage()));
        response.getWriter().flush();
    }

}