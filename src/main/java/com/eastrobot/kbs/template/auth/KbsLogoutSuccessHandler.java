package com.eastrobot.kbs.template.auth;

import com.eastrobot.kbs.template.config.JwtConfig;
import com.eastrobot.kbs.template.exception.ResponseEntity;
import com.eastrobot.kbs.template.exception.ResultCode;
import com.eastrobot.kbs.template.util.EnvironmentUtil;
import com.eastrobot.kbs.template.util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Slf4j
public class KbsLogoutSuccessHandler implements LogoutSuccessHandler {

    private JwtConfig jwtConfig = EnvironmentUtil.ofCtx().getBean(JwtConfig.class);

    private RedisTemplate redisTemplate = EnvironmentUtil.ofCtx().getBean(RedisTemplate.class);

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                Authentication authentication) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        try {
            Optional<String> userIdOpt = Optional.of(request.getHeader(jwtConfig.getAuthHeader()))
                    .filter(StringUtils::isNotBlank)
                    .map(JwtUtil::ofClaims)
                    .map(Claims::getId);
            if (userIdOpt.isPresent()) {
                // 退出删除jwt
                redisTemplate.delete(userIdOpt.get());
            }

            response.getWriter().write(ResponseEntity.ofSuccess(ResultCode.JWT_USER_LOGOUT.meaning).toString());
        } catch (Exception e) {
            log.error("on logout with error: {}", e.getMessage());
            response.getWriter().write(ResponseEntity.ofFailure(ResultCode.JWT_ILLEGAL_TOKEN, e.getMessage()).toString());
        }

        response.getWriter().flush();
    }
}