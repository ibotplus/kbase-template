package com.eastrobot.kbs.template.config;

import com.eastrobot.kbs.template.dao.repository.BaseRepositoryImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author <a href="yogurt_lei@foxmail.com">Yogurt_lei</a>
 * @version v1.0 , 2018-08-09 9:39
 */
@Configuration
@EnableJpaRepositories(value = "com.eastrobot.kbs.template.dao.repository",repositoryBaseClass = BaseRepositoryImpl.class)
public class JpaRepositoryConfig {

}
