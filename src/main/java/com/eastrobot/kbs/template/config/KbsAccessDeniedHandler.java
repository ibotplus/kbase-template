/*
 * Powered by http://www.xiaoi.com
 */
package com.eastrobot.kbs.template.config;

import com.eastrobot.kbs.template.auth.AuthUtil;
import com.eastrobot.kbs.template.exception.ResultCode;
import com.eastrobot.kbs.template.model.vo.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @version v1.0
 * @date 2019/11/19 11:49
 */
@Component
@Slf4j
public class KbsAccessDeniedHandler implements AccessDeniedHandler {

    /**
     * 无权访问
     * @param request
     * @param response
     * @param e
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        log.debug("[{}] ACCESS DENIED", request.getServletPath());
        AuthUtil.flushResponse(response, ResponseEntity.failure(ResultCode.ACCESSDENIED));
    }
}
