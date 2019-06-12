package com.eastrobot.kbs.template.auth.filter;

import com.eastrobot.kbs.template.config.JwtConfig;
import com.eastrobot.kbs.template.exception.ResponseEntity;
import com.eastrobot.kbs.template.exception.ResultCode;
import com.eastrobot.kbs.template.util.EnvironmentUtil;
import com.eastrobot.kbs.template.util.JwtUtil;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

/**
 * 鉴权过滤器
 *
 * @author <a href="yogurt_lei@foxmail.com">Yogurt_lei</a>
 * @version v1.0 , 2019-03-15 16:00
 */
@Slf4j
public class KbsJwtAuthorizationFilter extends BasicAuthenticationFilter {

    private JwtConfig jwtConfig = EnvironmentUtil.ofCtx().getBean(JwtConfig.class);

    private RedisTemplate redisTemplate = EnvironmentUtil.ofCtx().getBean(RedisTemplate.class);

    public KbsJwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        Optional<String> jwtOpt = Optional.ofNullable(request.getHeader(jwtConfig.getAuthHeader()))
                .filter(StringUtils::isNotBlank)
                .filter(v -> StringUtils.startsWithIgnoreCase(v, jwtConfig.getTokenHead()));

        if (!jwtOpt.isPresent()) {
            chain.doFilter(request, response);
            return;
        }

        String jwt = jwtOpt.get();


        // 1. 校验jwt
        Claims claims;
        try {
            claims = JwtUtil.ofClaims(jwt);
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SignatureException | ExpiredJwtException e) {
            log.info("jwt illegal: {} ", e.getMessage());
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.getWriter().write(ResponseEntity.ofFailure(ResultCode.JWT_ILLEGAL_TOKEN, e.getMessage()).toString());
            response.getWriter().flush();
            return;
        }

        // 2. 不存在的jwt 用户已退出
        if (!Optional.ofNullable(redisTemplate.opsForValue().get(claims.getId())).isPresent()) {
            log.info("jwt not exist, user may logout");
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.getWriter().write(ResponseEntity.ofFailure(ResultCode.JWT_USER_LOGOUT).toString());
            response.getWriter().flush();
            return;
        }

        // 3. 是否续签token
        Optional<String> renewOpt = JwtUtil.renewJwt(jwt);
        if (renewOpt.isPresent()) {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.getWriter().write(ResponseEntity.ofFailure(ResultCode.JWT_RENEW_TOKEN, renewOpt.get()).toString());
            return;
        }

        // 4. 构造令牌 成功登录
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(claims.getId(), null, new ArrayList<>());

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        super.doFilter(request, response, chain);
    }
}