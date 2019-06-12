package com.eastrobot.kbs.template.auth;

import com.eastrobot.kbs.template.config.JwtConfig;
import com.eastrobot.kbs.template.exception.ResponseEntity;
import com.eastrobot.kbs.template.util.EnvironmentUtil;
import com.eastrobot.kbs.template.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


/**
 * 处理验证成功的handler
 */
@Slf4j
public class KbsAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private JwtConfig jwtConfig = EnvironmentUtil.ofCtx().getBean(JwtConfig.class);

    private RedisTemplate redisTemplate = EnvironmentUtil.ofCtx().getBean(RedisTemplate.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication) throws IOException {
        log.info("AuthenticationSuccessHandler: generateJwt");
        // 校验成功 生成token,redis中存储该key,并设置过期时间, 客户端每次请求在HEAD中携带.
        String userId = authentication.getName();
        String jwt = JwtUtil.generateJwt(userId);
        redisTemplate.opsForValue().set(userId, jwt);
        redisTemplate.expire(userId, jwtConfig.getExpireInMinute(), TimeUnit.MINUTES);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().write(ResponseEntity.ofSuccess(JwtUtil.generateJwt(authentication.getName())).toString());
        response.getWriter().flush();
    }
}