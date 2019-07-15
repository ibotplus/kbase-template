package com.eastrobot.kbs.template.auth.filter;

import com.eastrobot.kbs.template.auth.AuthUtil;
import com.eastrobot.kbs.template.config.JwtConfig;
import com.eastrobot.kbs.template.exception.ResultCode;
import com.eastrobot.kbs.template.model.entity.ResponseEntity;
import com.eastrobot.kbs.template.util.JwtUtil;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 鉴权过滤器
 *
 * @author <a href="yogurt_lei@foxmail.com">Yogurt_lei</a>
 * @version v1.0 , 2019-03-15 16:00
 */
@Slf4j
@Component
public class KbsJwtAuthorizationFilter extends OncePerRequestFilter {

    @Resource
    private JwtConfig jwtConfig;

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        Optional<String> jwtOpt = Optional.ofNullable(request.getHeader(jwtConfig.getAuthHeader()))
                .filter(StringUtils::isNotBlank)
                .filter(v -> StringUtils.startsWithIgnoreCase(v, jwtConfig.getTokenHead()));

        if (jwtOpt.isPresent()) {
            String jwt = jwtOpt.get();

            // 1. 校验jwt
            String userId;
            try {
                Claims claims = JwtUtil.ofClaims(jwt);
                userId = claims.getId();
            } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SignatureException | ExpiredJwtException e) {
                log.warn("jwt illegal: {} ", e.getMessage());
                AuthUtil.flushResponse(response, ResponseEntity.ofFailure(ResultCode.JWT_ILLEGAL_TOKEN, e.getMessage()));
                return;
            }

            // 2. 不存在的jwt 用户已退出
            if (!Optional.ofNullable(redisTemplate.opsForValue().get(userId)).isPresent()) {
                log.warn("jwt not exist, user may logout");
                AuthUtil.flushResponse(response, ResponseEntity.ofFailure(ResultCode.JWT_USER_LOGOUT));
                return;
            }

            // 3. 是否续签token
            Optional<String> renewOpt = JwtUtil.renewJwt(jwt);
            if (renewOpt.isPresent()) {
                log.info("{} jwt has expired, renew jwt", userId);
                redisTemplate.opsForValue().set(userId, renewOpt.get());
                redisTemplate.expire(userId, jwtConfig.getExpireInMinute(), TimeUnit.MINUTES);
                AuthUtil.flushResponse(response, ResponseEntity.ofFailure(ResultCode.JWT_RENEW_TOKEN, renewOpt.get()));
                return;
            }

            // 4. 构造令牌 成功登录
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId,
                    null, new ArrayList<>());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        chain.doFilter(request, response);
    }
}