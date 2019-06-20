package com.eastrobot.kbs.template.auth;

import com.eastrobot.kbs.template.exception.ResultCode;
import com.eastrobot.kbs.template.model.entity.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class KbsAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        log.debug("[{}] UNAUTHORIZED", request.getServletPath());
        AuthUtil.flushResponse(response, ResponseEntity.ofFailure(ResultCode.UNAUTHORIZED));
    }
}
