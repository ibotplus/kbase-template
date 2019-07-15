package com.eastrobot.kbs.template.util;

import com.eastrobot.kbs.template.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Component
public class JwtUtil {

    private static PrivateKey privateKey;
    private static PublicKey publicKey;
    private static JwtConfig jwtConfig;

    static {
        //加载kbase-live-api.jks文件
        try (InputStream inputStream =
                     Thread.currentThread().getContextClassLoader().getResourceAsStream("jwt.jks")) {
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(inputStream, "ibotkbs".toCharArray());
            privateKey = (PrivateKey) keyStore.getKey("jwt", "ibotkbs".toCharArray());
            publicKey = keyStore.getCertificate("jwt").getPublicKey();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成Token
     *
     * @param userId 用户ID
     */
    public static String generateJwt(String userId) {
        // calculate the date
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime now = LocalDateTime.now();
        Date nowDate = Date.from(now.atZone(zoneId).toInstant());
        Date expireDate = Date.from(
                now.plusMinutes(jwtConfig.getExpireInMinute())
                        .atZone(zoneId)
                        .toInstant()
        );

        String jwt = Jwts.builder()
                .setIssuer(jwtConfig.getIssuer())
                .setSubject(userId)
                .setAudience(userId)
                .setExpiration(expireDate)
                .setNotBefore(nowDate)
                .setIssuedAt(nowDate)
                .setId(userId)
                .signWith(SignatureAlgorithm.RS256, privateKey)
                .compact();
        return jwtConfig.getTokenHead() + jwt; //jwt前面一般都会加Bearer
    }

    /**
     * 旧jwt到期续签
     */
    public static Optional<String> renewJwt(String OldJwt) {
        Claims claims = ofClaims(OldJwt);

        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiration = LocalDateTime.ofInstant(claims.getExpiration().toInstant(), zoneId);
        boolean needRenew = Duration.between(now, expiration).toMinutes() <= jwtConfig.getRefreshRemainLeftMinute();
        return Optional.ofNullable(needRenew ? generateJwt(claims.getId()) : null);
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     *
     * @return 数据声明
     */
    public static Claims ofClaims(String token) {
        return Jwts.parser()
                .setSigningKey(publicKey)
                .parseClaimsJws(token.substring(jwtConfig.getTokenHead().length()))
                .getBody();
    }


    @Autowired
    public void setJwtConfig(JwtConfig jwtConfig) {
        JwtUtil.jwtConfig = jwtConfig;
    }

}