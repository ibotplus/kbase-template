package com.eastrobot.kbs.template.auth;

import com.eastrobot.kbs.template.config.JwtConfig;
import com.eastrobot.kbs.template.model.entity.ResponseEntity;
import com.eastrobot.kbs.template.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


/**
 * 处理验证成功的handler
 */
@Slf4j
@Component
public class KbsAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Resource
    private JwtConfig jwtConfig;

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        log.info("AuthenticationSuccessHandler: generateJwt");
        // 校验成功 生成token,redis中存储该key,并设置过期时间, 客户端每次请求在HEAD中携带.
        String userId = authentication.getName();
        String jwt = JwtUtil.generateJwt(userId);
        redisTemplate.opsForValue().set(userId, jwt);
        redisTemplate.expire(userId, jwtConfig.getExpireInMinute(), TimeUnit.MINUTES);

        AuthUtil.flushResponse(response, ResponseEntity.ofSuccess(jwt));
    }
}