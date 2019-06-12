package com.eastrobot.kbs.template.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="yogurt_lei@foxmail.com">Yogurt_lei</a>
 * @version v1.0 , 2019-03-14 17:51
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties("kbs.jwt")
public class JwtConfig {
    /**
     * 签发者
     */
    private String issuer;
    /**
     * 客户端发送的head中的key
     */
    private String AuthHeader;
    /**
     * jwt有效期(单位:分钟): 换句话说, 类似之前的会话超时时间
     */
    private long expireInMinute;
    /**
     * token头 e.g.(Bearer )
     */
    private String tokenHead;
    /**
     * 在jwt还剩余多长时间刷新token(单位:分钟)
     */
    private long refreshRemainLeftMinute;
}