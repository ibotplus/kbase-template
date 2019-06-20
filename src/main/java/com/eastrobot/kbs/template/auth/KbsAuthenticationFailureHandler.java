package com.eastrobot.kbs.template.auth;

import com.eastrobot.kbs.template.exception.ResultCode;
import com.eastrobot.kbs.template.model.entity.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理校验失败的handler
 */
@Slf4j
@Component
public class KbsAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {

        ResultCode resultCode = ResultCode.UNDEFINED_SERVER_EXCEPTION;
        if (exception instanceof SessionAuthenticationException) {
            // session 限制, 用户重复登录
            resultCode = ResultCode.AUTH_SESSION_AUTHENTICATION;
        } else if (exception instanceof UsernameNotFoundException) {
            // 用户不存在
            resultCode = ResultCode.AUTH_USER_NOT_FOUND;
        } else if (exception instanceof BadCredentialsException) {
            // 密码错误
            resultCode = ResultCode.AUTH_BAD_CREDENTIALS;
        } else if (exception instanceof AccountStatusException) {
            // 账户状态异常
            resultCode = ResultCode.AUTH_ABNORMAL_ACCOUNT_STATUS;
        }

        AuthUtil.flushResponse(response, ResponseEntity.ofFailure(resultCode, exception.getMessage()));
    }

}