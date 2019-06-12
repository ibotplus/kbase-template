package com.eastrobot.kbs.template.config;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="yogurt_lei@foxmail.com">Yogurt_lei</a>
 * @version v1.0 , 2019-04-22 10:03
 */
@Slf4j
@Getter
@Setter
@Configuration
@ConfigurationProperties("kbs")
public class KbsProperties {
}
