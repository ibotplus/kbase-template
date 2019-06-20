package com.eastrobot.kbs.template.config;

import com.baomidou.mybatisplus.extension.parsers.BlockAttackSqlParser;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import com.google.common.collect.Lists;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author <a href="yogurt_lei@foxmail.com">Yogurt_lei</a>
 * @version v1.0 , 2019-06-17 10:27
 */
@Configuration
@MapperScan("com.eastrobot.kbs.template.dao.mapper")
public class MybatisConfig {

    /**
     * SQL执行效率插件
     */
    @Bean
    @Profile({"dev", "test"})
    public PerformanceInterceptor performanceInterceptor() {
        return new PerformanceInterceptor().setMaxTime(100).setFormat(false).setWriteInLog(true);
    }

    /**
     * 阻止恶意的全表更新删除
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        // 攻击 SQL 阻断解析器、加入解析链
        return (PaginationInterceptor) new PaginationInterceptor().setSqlParserList(Lists.newArrayList(new BlockAttackSqlParser()));
    }

    /**
     * 乐观锁拦截 字段必须要有@Version 则更新时 `set version = newVersion where version = oldVersion`
     * 仅支持 updateById(id) 与 update(entity, wrapper) ,且在 update(entity, wrapper) 方法下, wrapper 不能复用!!!
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }
}
